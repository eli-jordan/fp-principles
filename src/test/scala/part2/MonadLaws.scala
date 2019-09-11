package part2

import org.scalatest.{FlatSpec, MustMatchers}
import part2.data._
import part2.monads._

class MonadLaws extends FlatSpec with MustMatchers {

  def leftIdentityLaw[F[_], A, B](a: A, f: A => F[B])(implicit F: Monad[F]): Unit = {
    F.pure(a).flatMap(f) mustBe f(a)
  }

  def rightIdentityLaw[F[_], A](fa: F[A])(implicit F: Monad[F]): Unit = {
    fa.flatMap(F.pure) mustBe fa
  }

  def associativityLaw[F[_], A, B, C](fa: F[A], fn: A => F[B], gn: B => F[C])(implicit F: Monad[F]): Unit = {
    val left  = fa.flatMap(fn).flatMap(gn)
    val right = fa.flatMap(a => fn(a).flatMap(gn))

    left mustBe right
  }

  behavior of "Maybe monad"

  it should "obey the left identity law" in {
    leftIdentityLaw("foo", Maybe.defined[String](_))
    leftIdentityLaw("bar", (_: String) => Maybe.undefined[String])
  }

  it should "obey the right identity law" in {
    rightIdentityLaw(Maybe.defined("foo"))
    rightIdentityLaw(Maybe.undefined[String])
  }

  it should "obey the associativity law" in {
    associativityLaw(Maybe.defined("foo"), Maybe.defined[String](_), Maybe.defined[String](_))
    associativityLaw(Maybe.undefined[String], Maybe.defined[String](_), Maybe.defined[String](_))
    associativityLaw(
      Maybe.defined("foo"),
      (_: String) => Maybe.undefined[String],
      (_: String) => Maybe.undefined[String])
    associativityLaw(Maybe.undefined[String], Maybe.defined[String](_), (_: String) => Maybe.undefined[String])
    associativityLaw(Maybe.undefined[String], (_: String) => Maybe.undefined[String], Maybe.defined[String](_))
  }

  behavior of "CanFail monad"

  it should "obey the left identity law" in {
    leftIdentityLaw("foo", CanFail.success[String, String](_))
    leftIdentityLaw("bar", CanFail.fail[String, String](_))
  }

  it should "obey the right identity law" in {
    rightIdentityLaw(CanFail.success("foo"))
    rightIdentityLaw(CanFail.fail("bar"))
  }

  it should "obey the associativity law" in {
    associativityLaw(CanFail.success("foo"), CanFail.success[String, String](_), CanFail.success[String, String](_))
    associativityLaw(CanFail.fail("bar"), CanFail.success[String, String](_), CanFail.success[String, String](_))
    associativityLaw(CanFail.success("foo"), CanFail.fail[String, String](_), CanFail.fail[String, String](_))
    associativityLaw(CanFail.fail("bar"), CanFail.success[String, String](_), CanFail.fail[String, String](_))
    associativityLaw(CanFail.fail("bar"), CanFail.fail[String, String](_), CanFail.success[String, String](_))
  }
}
