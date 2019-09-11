package part2

import org.scalatest.{FlatSpec, MustMatchers}
import part2.applicatives._
import part2.data._
import part2.functors._

class ApplicativeLaws extends FlatSpec with MustMatchers {

  def leftIdentityLaw[F[_], A](fa: F[A])(implicit F: Applicative[F]): Unit =
    F.pure(()).zip(fa).map(_._2) mustBe fa

  def rightIdentityLaw[F[_], A](fa: F[A])(implicit F: Applicative[F]): Unit =
    fa.zip(F.pure(())).map(_._1) mustBe fa

  def associativityLaw[F[_], A, B, C](fa: F[A], fb: F[B], fc: F[C])(implicit F: Applicative[F]): Unit = {
    val left  = fa.zip(fb.zip(fc))
    val right = fa.zip(fb).zip(fc).map { case ((a, b), c) => (a, (b, c)) }

    left mustBe right
  }

  behavior of "Maybe applicative"

  it should "obey the left identity law" in {
    leftIdentityLaw(Maybe.defined("foo"))
    leftIdentityLaw(Maybe.undefined)
  }

  it should "obey the right identity law" in {
    rightIdentityLaw(Maybe.defined("foo"))
    rightIdentityLaw(Maybe.undefined)
  }

  it should "obey the associativity law" in {
    associativityLaw(Maybe.defined("foo"), Maybe.defined("bar"), Maybe.defined("baz"))
    associativityLaw(Maybe.undefined[String], Maybe.undefined[String], Maybe.undefined[String])
    associativityLaw(Maybe.defined("foo"), Maybe.undefined[String], Maybe.undefined[String])
    associativityLaw(Maybe.undefined[String], Maybe.defined("foo"), Maybe.undefined[String])
    associativityLaw(Maybe.undefined[String], Maybe.undefined[String], Maybe.defined("foo"))
  }

  behavior of "CanFail applicative"

  it should "obey the left identity law" in {
    leftIdentityLaw(CanFail.success("foo"))
    leftIdentityLaw(CanFail.fail("bar"))
  }

  it should "obey the right identity law" in {
    rightIdentityLaw(CanFail.success("foo"))
    rightIdentityLaw(CanFail.fail("bar"))
  }

  it should "obey the associativity law" in {
    associativityLaw(CanFail.success("foo"), CanFail.success("bar"), CanFail.success("baz"))
    associativityLaw(CanFail.fail("bar"), CanFail.fail("bar"), CanFail.fail("bar"))
    associativityLaw(CanFail.success("foo"), CanFail.fail("bar"), CanFail.fail("bar"))
    associativityLaw(CanFail.fail("bar"), CanFail.success("foo"), CanFail.fail("bar"))
    associativityLaw(CanFail.fail("bar"), CanFail.fail("bar"), CanFail.success("foo"))
  }
}
