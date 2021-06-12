package controllers

import domains.Account
import services.AccountService
import play.api.mvc.{ActionBuilder, AnyContent, BodyParser, PlayBodyParsers, Request, Result, Results, WrappedRequest}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

case class UserRequest[A](eMail: String, password: String, request: Request[A]) extends WrappedRequest[A](request)

class SecureAction @Inject() (parsers: PlayBodyParsers, ec: ExecutionContext) extends ActionBuilder[UserRequest, AnyContent] with Results {

  override def parser: BodyParser[AnyContent] =
    parsers.default

  override protected def executionContext: ExecutionContext =
    ec

  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
      val eMail = request.headers.get("e-mail").fold("")(identity)
      val password = request.headers.get("password").fold("")(identity)
      if (AccountService.getAllAccounts().contains(Account(eMail, password))) {
        block(UserRequest(eMail, password, request))
      } else {
        Future.successful(Unauthorized("Unauthorized access!!"))
      }
    }
}
