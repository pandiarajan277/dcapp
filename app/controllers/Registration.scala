package controllers

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
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.impl.Future
import services.db.DetailsToDB
import scala.concurrent.Future
import services.db.DetailsFromDB

class Registration extends Controller {

  //UsersRow(firstname: String, lastname: Option[String] = None, dob: java.sql.Date, email: String, password: String, createddate: java.sql.Date)

  val registrationForm = Form(
    mapping(
      "firstname" -> nonEmptyText,
      "lastname" -> optional(text),
      "dob" -> sqlDate,
      "email" -> email,
      "password" -> nonEmptyText,
      "createddate" -> default(sqlDate, new java.sql.Date(Calendar.getInstance().getTime().getTime())))(UsersRow.apply)(UsersRow.unapply))

  def index = (Action andThen new HomeController().notloggedInCheckAction) {

    Ok(views.html.registration(registrationForm))
  }

  def register = createUser

  def createUser = Action.async { implicit request: Request[AnyContent] =>
    val errorFunction = { formWithErrors: Form[UsersRow] =>
      Future(BadRequest(views.html.registration(formWithErrors)))
    }

    val successFunction = { data: UsersRow =>
      DetailsFromDB.getUser(data.email) flatMap {
        x =>
          x match {
            case x if x.length > 0 => {
              val errorForm = registrationForm.bindFromRequest().withError("email", "Invalid email already exists", "")
              Future(BadRequest(views.html.registration(errorForm)))
            }
            case _ => DetailsToDB.createUser(data).map(_ => Redirect(routes.Registration.index())) recover { case a => Redirect(routes.HomeController.index()) }
          }
      }
    }

    val formValidationResult = registrationForm.bindFromRequest

    formValidationResult.fold(errorFunction, successFunction)
  }

}