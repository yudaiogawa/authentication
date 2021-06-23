package services

import domains.Account

trait AccountService {

  private val accounts = List(
    Account("alice@example.com", "yJ2Z#bL7vi2u@RDe"),
    Account("bob@example.com", "N#BoXNM^9CBA*2Dx"),
    Account("carol@example.com", "kC2LC$8aavY84n7Y")
  )

  def getAllAccounts: List[Account] =
    accounts

  def findByEmail(email: String): Option[Account] =
    accounts.find(_.eMail == email)
}

object AccountService extends AccountService
