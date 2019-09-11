package part1

object typeclasses {

  // This is the most concrete combine function we will see in this section
  def combine1(xs: List[Int]): Int =
    xs.foldLeft(0)(_ + _)

  // Exercise 1 - Implement combine2

  trait Monoid[A] {
    def zero: A
    def combine(a: A, b: A): A
  }

  lazy val addition: Monoid[Int] = ???
  def combine2(xs: List[Int], monoid: Monoid[Int]): Int = {
    ???
  }

  // Exercise 2 - Add a test for the generified combine2 function, that combines a list of strings
  // Tip: You will need to implement the `concat` Monoid first
  lazy val concat: Monoid[String] = ???



  // Exercise 3 - Implicit monoids.
  //
  // Tip: Use the context bound `A: Monoid`

  object Monoid {
    implicit val intAddition: Monoid[Int] = addition
    implicit val stringConcat: Monoid[String] = concat

    // This is a "summoning" method. It allows a monoid typeclass
    // instance to be looked up for a particular types.
    // For example Monoid[String] will look for an implicit Monoid[String]
    // and if its defined return it.
    //
    // This is quite similar to the way the implicitly function works.
    def apply[A](implicit ev: Monoid[A]): Monoid[A] = ev
  }

  def combine3[A](xs: List[A]): A = ???

  // Exercise 4 - dot syntax for Monoid
  // Tip: Use the context bound `A: Monoid`
  implicit class MonoidOps4[A](xs: List[A]) {
    def combine4: A = ???
  }

  // Exercise 5 (optional) - abstracting away from list
  //
  // Recall that F[_] is a type constructor, and can be defined to be any
  // type that that one argument. List or Vector for example.
  //
  // To implement this exercise you will need to do several things.
  //   1. Define a `FoldLeft` typeclass instance for `List` and `Vector`
  //   2. Make the `FoldLeft` for F[_] available in `MonoidOps5`
  //   3. Make the `Monoid` for A available in `MonoidOps5`
  //   4. Uses these typeclass instances to implement combine5

  trait FoldLeft[F[_]] {
    def foldLeft[A, B](b: B, xs: F[A], f: (B, A) => B): B
  }

  implicit class MonoidOps5[F[_], A](xs: F[A]) {
    def combine5: A = ???
  }
}
