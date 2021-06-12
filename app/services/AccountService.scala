package services

import domains.Account

trait AccountService {

  def getAllAccounts(): List[Account] = {
    List(
      Account("alice@example.com", "yJ2Z#bL7vi2u@RDe"),
      Account("bob@example.com", "N#BoXNM^9CBA*2Dx"),
      Account("carol@example.com", "kC2LC$8aavY84n7Y"))
  }
}

object AccountService extends AccountService
