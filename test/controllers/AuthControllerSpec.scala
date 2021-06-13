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

    "signup with posting the json" in {
      val json = Json.parse("""{"e-mail": "alice@example.com", "password": "yJ2Z#bL7vi2u@RDe"}""")
      val req = FakeRequest(POST, "/signup").withHeaders((CONTENT_TYPE, "application/json")).withJsonBody(json)
      val signup = call(controller.signup(), req)

      status(signup) mustBe OK
    }

    "signin with the json" in {
      val json = Json.parse("""{"e-mail": "alice@example.com", "password": "yJ2Z#bL7vi2u@RDe"}""")
      val req = FakeRequest(GET, "/signin").withHeaders((CONTENT_TYPE, "application/json")).withJsonBody(json)
      val signin = call(controller.signin(), req)

      status(signin) mustBe UNAUTHORIZED
    }
  }
}
