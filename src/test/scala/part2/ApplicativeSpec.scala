package part2

import org.scalatest.{FlatSpec, MustMatchers}
import data._
import applicatives._

class ApplicativeSpec extends FlatSpec with MustMatchers {
  behavior of "asInts"

  it should "successfully perform the conversion" in {
    asInts(List("1", "2", "3")) mustBe Success(List(1, 2, 3))
  }

  it should "fail on a String that doesn't represent an Int" in {
    asInts(List("1aa", "2", "3")) mustBe a[Failure[_, _]]
  }
}
