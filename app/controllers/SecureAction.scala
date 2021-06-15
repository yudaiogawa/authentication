package controllers

import play.api.cache.AsyncCacheApi
import play.api.mvc.{ActionBuilder, AnyContent, BodyParser, PlayBodyParsers, Request, Result, Results, WrappedRequest}
import services.AccountService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

case class UserRequest[A](sessionId: String, eMail: String, password: String, request: Request[A]) extends WrappedRequest[A](request)

class SecureAction @Inject() (parsers: PlayBodyParsers, cache: AsyncCacheApi, implicit val ec: ExecutionContext) extends ActionBuilder[UserRequest, AnyContent] with Results {

  override def parser: BodyParser[AnyContent] =
    parsers.default

  override protected def executionContext: ExecutionContext =
    ec

  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    request.session.get("sessionId").map { sessionId =>
      cache.get[String](sessionId).flatMap { optEmail =>
        val result = for {
          email <- optEmail // Option[String]
          account <- AccountService.findByEmail(email) // Option[Account]
        } yield block(UserRequest(account.sessionId, account.eMail, account.password, request))

        result.getOrElse(Future.successful(Unauthorized("Unauthorized access!!"))) // Option[Result]
      }
    }.getOrElse(
      Future.successful(Unauthorized("Unauthorized access!!"))
    )
  }
}
