package part2

import org.scalatest.{FlatSpec, MustMatchers}
import functors._
import part2.data.{Failure, Success}

class FunctorSpec extends FlatSpec with MustMatchers {

  behavior of "greeting"

  it should "generate the correct greeting" in {
    System.setProperty("name", "Joe")
    greeting mustBe Success("Hello Joe, and welcome to functor town")
  }

  it should "fail if the name property is not set" in {
    System.clearProperty("name")
    greeting mustBe a[Failure[_, _]]
  }

}
