package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play.materializer
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

class AuthControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "AuthController Test" should {
    def controller = inject[AuthController]

    "sign-up with posting the json" in {
      val json = Json.parse("""{"e-mail": "alice@example.com"}""")
      val response = controller
        .signup()
        .apply(FakeRequest(POST, "/signup").withJsonBody(json))

      status(response) mustBe OK
      contentAsString(response) must include("alice@example.com")
    }

    "me with session" in {
      val response = controller
        .me()
        .apply(FakeRequest(GET, "/me").withSession("sessionId" -> "123456789"))

      status(response) mustBe UNAUTHORIZED
    }

    // TODO:
    //   * meで200が返却されるテスト
    //   * signupしてcookie取得して、cookieにセットされたsessionIdを利用してmeにアクセスする。
  }
}
