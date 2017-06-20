package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.impl.Future
import scala.concurrent.Future

case class UserData(name: String, age: Int)
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  val userForm = Form(
    mapping(
      "name" -> text,
      "age" -> number)(UserData.apply)(UserData.unapply))

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def notloggedInCheckAction = new ActionRefiner[Request, Request] {

    @Override def refine[A](request: Request[A]) = {
      Future {
        request.session.get("connected").map { user =>
          Left(Redirect(routes.LoginController.welcome()))
        }.getOrElse(Right(request))
      }
    }
  }

  def index =(Action andThen notloggedInCheckAction) {
   
    Ok(views.html.index())
  }

}
