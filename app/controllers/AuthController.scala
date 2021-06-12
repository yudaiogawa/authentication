package controllers

import javax.inject._
import play.api.libs.json.JsValue
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class AuthController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def save() = Action { request: Request[AnyContent] =>
    val body: AnyContent = request.body
    val jsonBody: Option[JsValue] = body.asJson
    jsonBody
      .map {json =>
        Ok("signup: " + (json \ "e-mail").as[String] + ", " + (json \ "password").as[String])
      }
      .getOrElse {
        BadRequest("Expecting application/json request body")
      }
  }

  def accountHome = {
    SecureAction.async {
      implicit request =>
        Future.successful(Ok("Hello!! " + request.eMail))
    }
  }
}
