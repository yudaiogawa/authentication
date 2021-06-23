package controllers

import javax.inject._
import play.api.mvc._
import play.api.cache._

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.duration._

@Singleton
class AuthController @Inject()(secureAction: SecureAction, cache: AsyncCacheApi, val controllerComponents: ControllerComponents) extends BaseController {

  // cache
  // key = token ( ${username}-token-${UUID} )
  // value = json ( "{ token: $KEY, id: ${userid}, name: ${username}, expairation: date }" )

  // cache (temporary implementation)
  // key = ${UUID}
  // value = ${email}

  def signup(): Action[AnyContent] = {
    Action.apply { implicit request =>
      request.body.asJson.map { json =>
        val email = (json \ "e-mail").as[String]
        val sessionId = UUID.randomUUID().toString

        cache.set(sessionId, email, 6.hours)

        Ok(s"Thank you for subscribing!! [e-mail] $email [sessionId] $sessionId")
          .withSession(request.session + ("sessionId" -> sessionId))
      }.getOrElse(BadRequest)
    }
  }

  def me(): Action[AnyContent] = {
    secureAction.async { implicit request =>
      Future.successful(
        Ok(s"Hello!! [e-mail] ${request.eMail} [SessionId] ${request.sessionId}"))
    }
  }
}
