package controllers

import javax.inject._
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class AuthController @Inject()(secureAction: SecureAction, val controllerComponents: ControllerComponents) extends BaseController {

  def signup(): Action[AnyContent] = Action { implicit request =>
    request
      .body
      .asJson
      .map { json =>
        Ok("signup: " + (json \ "e-mail").as[String] + ", " + (json \ "password").as[String])
      }
      .getOrElse {
        BadRequest("Expecting application/json request body")
      }
  }

  def signin(): Action[AnyContent] = {
    secureAction.async { implicit request =>
      Future.successful(Ok(s"Hello!! ${request.eMail}. Session id is ${request.sessionId}."))
    }
  }
}
