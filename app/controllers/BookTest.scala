package controllers


import java.lang.ProcessBuilder.Redirect

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import models.Tables.{LabsRow, TestRow, TesttakenRow, UsersRow}
import java.util.Calendar

import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import play.api.i18n._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import services.db.DetailsFromDB._
import services.db.DetailsToDB

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.impl.Future
import scala.concurrent.Future

class TestRequest[A](val test: TestRow, val userRequest: UserRequest[A]) extends WrappedRequest[A](userRequest)

class LabRequest[A](val lab: LabsRow, val testRequest: TestRequest[A]) extends WrappedRequest[A](testRequest)

class BookTest extends Controller {
  val searchFormTuple = Form(
    tuple(
      "city" -> nonEmptyText,
      "zipcode" -> nonEmptyText)
  )

  val sampleAddressForm = Form(
    tuple(
      "sampleaddress" -> nonEmptyText,
      "testdate" -> default(sqlDate, new java.sql.Date(Calendar.getInstance().getTime().getTime()))
    )
  )

  def searchLabsForTest(testId: Int) = (Action andThen new LoginController().loggedInCheckAction) async { request =>

    getTestDetails(testId).map {
      case Some(x)
      => val id = testId.toString
        Ok(views.html.booktest(x, scala.Seq.empty[LabsRow], searchFormTuple)).withSession(
          request.session + ("test" -> id))

      case _ => Forbidden
    }

  }


  def testSelectedCheckAction = new ActionRefiner[UserRequest, TestRequest] {

    @Override def refine[A](request: UserRequest[A]) = {
      request.session.get("test").map { test =>
        getTestDetails(request.session.get("test").get.toInt).map {
          case Some(x) => Right(new TestRequest(x, request))
          case None => Left(Forbidden)
        } recover { case a => Left(Redirect(routes.HomeController.index())) }
      }.getOrElse(Future(Left(Ok(views.html.index()))))
    }
  }


  def searchLabCheckAction(id: Int) = new ActionRefiner[TestRequest, LabRequest] {

    @Override def refine[A](request: TestRequest[A]) = {
      getTopRecord(getLab(id)).map {
        case Some(x) => Right(new LabRequest(x, request))
        case None => Left(Forbidden)
      } recover { case _ => Left(Redirect(routes.HomeController.index())) }

    }
  }

  //
  def listLabForLoc() = (Action andThen new LoginController().loggedInCheckAction andThen testSelectedCheckAction) async {
    implicit request =>

      val test = request.test
      val errorFunction = { formWithErrors: Form[(String, String)] =>
        Future(BadRequest(views.html.booktest(test, scala.Seq.empty[LabsRow], formWithErrors)))
      }

      val successFunction = { data: (String, String) =>
        getLabsFromLoc(data._1, data._2).map {

          case x if x.isEmpty =>
            Ok(views.html.booktest(test, x, searchFormTuple.bindFromRequest().withGlobalError("not found a valid record", "")))

          case x => Ok(views.html.booktest(test, x, searchFormTuple))
        }
      }

      val userFormTupleResult = searchFormTuple.bindFromRequest
      userFormTupleResult.fold(errorFunction, successFunction)
  }

  def labSelectedCheckAction = new ActionRefiner[TestRequest, LabRequest] {

    @Override def refine[A](request: TestRequest[A]) = {
      request.session.get("lab").map { lab =>
        getTopRecord(getLab(lab.toInt)).map {
          case Some(x) => Right(new LabRequest(x, request))
          case None => Left(Forbidden)
        } recover { case e => Left(InternalServerError(views.html.errorpage(e.getMessage))) }
      }.getOrElse(Future(Left(Forbidden)))
    }
  }

  def submitDetails = (Action andThen new LoginController().loggedInCheckAction andThen testSelectedCheckAction andThen labSelectedCheckAction) async {
    implicit request =>
      val user = request.testRequest.userRequest.user
      val test = request.testRequest.test
      val lab = request.lab
      val errorFunction = { formWithErrors: Form[(String, java.sql.Date)] =>
        Future(BadRequest(views.html.SampleCollectionAddress(test, lab, formWithErrors)))
      }

      val successFunction = {
        data: (String, java.sql.Date) =>
          if (data._2.compareTo(new java.sql.Date(Calendar.getInstance().getTime().getTime())) >= 0) {
            val testRow = TesttakenRow(Some(user.email), test.id, data._2, lab.id, data._1)
            DetailsToDB.insertTest(testRow).map {
              _ => Ok("done  <a href=\"" + routes.LoginController.welcome() + "\">go home</a> ").as(HTML)
            } recover {
              case e => InternalServerError(views.html.errorpage(e.getMessage))
            }
          }
          else {
            val sampleAddressFormResult = sampleAddressForm.bindFromRequest().withError("testdate", "date cannot be past date", "")
            Future(Ok(views.html.SampleCollectionAddress(test, lab, sampleAddressFormResult)))
          }
      }

      val sampleAddressFormResult = sampleAddressForm.bindFromRequest
      sampleAddressFormResult.fold(errorFunction, successFunction)
  }

  def takeTest(id: Int) = (Action andThen new LoginController().loggedInCheckAction andThen testSelectedCheckAction andThen searchLabCheckAction(id: Int)) {
    implicit request =>
      //TesttakenRow(email: Option[String] = None, testid: Int, takendate: java.sql.Date, labid: Option[Int] = None, useraddress: String)
      val user = request.testRequest.userRequest.user
      val test = request.testRequest.test
      val lab = request.lab

      Ok(views.html.SampleCollectionAddress(test, lab, sampleAddressForm)).withSession(request.session + ("lab" -> lab.id.get.toString))
  }
}