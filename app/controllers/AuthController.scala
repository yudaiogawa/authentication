package controllers

import javax.inject._
import play.api.mvc._

import java.util.UUID
import scala.concurrent.Future

@Singleton
class AuthController @Inject()(secureAction: SecureAction, val controllerComponents: ControllerComponents) extends BaseController {

  def signup(): Action[AnyContent] = {
    Action.apply { implicit request =>
      request.body.asJson.map { json =>
        val email = (json \ "e-mail").as[String]
        val sessionId = UUID.randomUUID().toString
        Ok(s"Thank you for subscribing!! [e-mail] $email [sessionId] $sessionId")
          .withSession(request.session + ("sessionId" -> sessionId))
      }.getOrElse(BadRequest)
    }
  }

  def signin(): Action[AnyContent] = {
    secureAction.async { implicit request =>
      Future.successful(Ok(s"Hello!! ${request.eMail}. Session id is ${request.sessionId}."))
    }
  }
}
