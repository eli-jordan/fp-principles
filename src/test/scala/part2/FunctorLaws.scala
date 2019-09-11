package part2

import org.scalatest.{FlatSpec, MustMatchers}
import part2.data._
import part2.functors._

class FunctorLaws extends FlatSpec with MustMatchers {

  def identityLaw[F[_]: Functor, A](fa: F[A]): Unit = {
    fa.map(identity) mustBe fa
  }

  def compositionLaw[F[_]: Functor, A, B, C](fa: F[A], ab: A => B, bc: B => C): Unit = {
    fa.map(ab).map(bc) mustBe fa.map(ab andThen bc)
  }

  behavior of "Maybe functor"

  it should "obey the identity law" in {
    identityLaw(Undefined: Maybe[String])
    identityLaw(Defined("foo"): Maybe[String])
  }

  it should "obey the composition law" in {
    val len: String => Int = _.length
    val inc: Int => Int    = _ + 1

    compositionLaw(Undefined: Maybe[String], len, inc)
    compositionLaw(Defined("foo-bar-baz"): Maybe[String], len, inc)
  }

  behavior of "CanFail functor"

  it should "obey the identity law" in {
    val success: CanFail[String, String] = Success("success")
    val fail: CanFail[String, String]    = Failure("failure")

    identityLaw(success)
    identityLaw(fail)
  }

  it should "obey the composition law" in {
    val success: CanFail[String, String] = Success("success")
    val fail: CanFail[String, String]    = Failure("failure")

    val len: String => Int = _.length
    val inc: Int => Int    = _ + 1

    compositionLaw(success, len, inc)
    compositionLaw(fail, len, inc)
  }
}
