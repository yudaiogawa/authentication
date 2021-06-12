package controllers

import domains.Account
import services.AccountService

import play.api.mvc.{ActionBuilder, Request, Result, Results, WrappedRequest}

import scala.concurrent.Future

case class UserRequest[A](eMail: String, password: String, request: Request[A]) extends WrappedRequest[A](request)

object SecureAction extends ActionBuilder[UserRequest] {
  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    val eMail = request.headers.get("e-mail").fold("")(identity)
    val password = request.headers.get("password").fold("")(identity)
    if (AccountService.getAllAccounts().contains(Account(eMail, password))) {
      block(UserRequest(eMail, password, request))
    } else {
      Future.successful(Results.Unauthorized("Unauthorized access!!"))
    }
  }
}
