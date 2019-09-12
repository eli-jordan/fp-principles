package part1

import part1.typeclasses._
import org.scalatest.{FlatSpec, MustMatchers, Tag}

object tag {
  object combine1 extends Tag("typeclasses.combine1")
  object combine2 extends Tag("typeclasses.combine2")
  object combine3 extends Tag("typeclasses.combine3")
  object combine4 extends Tag("typeclasses.combine4")
  object combine5 extends Tag("typeclasses.combine5")
}


class TypeclassesSpec extends FlatSpec with MustMatchers {



  behavior of "combine1"
  it should "combine a List[Int]" taggedAs tag.combine1 in {
    combine1(List(1, 2, 3)) mustBe 6
  }

  behavior of "combine2"
  it should "combine a List[Int]" taggedAs tag.combine2 in {
    combine2(List(1, 2, 3), addition) mustBe 6
  }

  it should "combine a List[String]" taggedAs tag.combine2 in {
    //TODO: Exercise 2 - Implement this test
    combine2(List("1", "2", "3"), concat) mustBe "123"
  }

  behavior of "combine3"
  it should "combine a List[Int]" taggedAs tag.combine3 in {
    combine3(List(1, 2, 3)) mustBe 6
  }

  it should "combine a List[String]" taggedAs tag.combine3 in {
    combine3(List("1", "2", "3")) mustBe "123"
  }

  behavior of "combine4"
  it should "combine a List[Int]" taggedAs tag.combine4 in {
    List(1, 2, 3).combine4 mustBe 6
  }

  it should "combine a List[String]" taggedAs tag.combine4 in {
    List("1", "2", "3").combine4 mustBe "123"
  }

  behavior of "combine5"
  it should "combine a List[Int]" taggedAs tag.combine5 in {
    List(1, 2, 3).combine5 mustBe 6
  }

  it should "combine a Vector[String]" taggedAs tag.combine5 in {
    Vector("1", "2", "3").combine5 mustBe "123"
  }
}
