package controllers

import java.lang.ProcessBuilder.Redirect

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import models.Tables.UsersRow
import java.util.Calendar

import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import play.api.i18n._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import services.db.DetailsFromDB._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.impl.Future
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.impl.Future

class UserRequest[A](val user: UsersRow, val request: Request[A]) extends WrappedRequest[A](request)

class LoginController extends Controller {

  val userFormTuple = Form(
    tuple(
      "email" -> email,
      "password" -> nonEmptyText) // tuples come with built-in apply/unapply
  )

  def loginUser: Action[AnyContent] = (Action andThen new HomeController().notloggedInCheckAction) {
    Ok(views.html.login(userFormTuple))
  }

  def logout = Action {
    Redirect(routes.HomeController.index()).withNewSession
  }


  def loggedInCheckAction = new ActionRefiner[Request, UserRequest] {

    @Override def refine[A](request: Request[A]) = {
      {
        request.session.get("connected").map { user =>
          //UsersRow(firstname: String, lastname: Option[String] = None, dob: java.sql.Date, email: String, password: String, createddate: java.sql.Date)
          getTopRecord(getUser(user)).map {
            case Some(x) => Right(new UserRequest(x, request))
            case None => Left(Ok("fo"))
          }

        }.getOrElse(Future(Left(Ok(views.html.login(userFormTuple)))))
      }
    }
  }

  def welcome() = (Action andThen loggedInCheckAction) async { request =>
    request.session.get("connected").map { user =>
      getAllLabTest().map(x => Ok(views.html.welcome(user, x)))
    }.get
  }

  def login() = Action.async {
    implicit request =>
      val errorFunction = { formWithErrors: Form[(String, String)] =>
        Future(BadRequest(views.html.login(formWithErrors)))
      }

      val successFunction = { data: (String, String) =>
        getUser(data._1, data._2) flatMap {
          case x if x.isEmpty => {
            val errorForm = userFormTuple.bindFromRequest().withGlobalError("invalid email/password", "")
            Future(BadRequest(views.html.login(errorForm)))
          }
          case _ => Future(Redirect(routes.LoginController.welcome()).withSession(
            "connected" -> data._1))
        }
      }

      val userFormTupleResult = userFormTuple.bindFromRequest
      userFormTupleResult.fold(errorFunction, successFunction)
  }

}


