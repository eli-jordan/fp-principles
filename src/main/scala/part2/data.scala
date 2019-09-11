package part2

object data {

  // Models the semantics of optionality
  //
  // This type is equivalent to scala.Option
  sealed trait Maybe[+A]
  object Maybe {
    def defined[A](a: A): Maybe[A] = Defined(a)
    def undefined[A]: Maybe[A]     = Undefined
  }
  case class Defined[A](a: A) extends Maybe[A]
  case object Undefined       extends Maybe[Nothing]

  // Models the semantics of short circuiting on failure
  //
  // This type is equivalent to scala.util.Either
  sealed trait CanFail[+A, +B]
  object CanFail {
    def success[A, B](b: B): CanFail[A, B] = Success(b)
    def fail[A, B](a: A): CanFail[A, B]    = Failure(a)

    // Takes a by-name parameter, that may throw exceptions.
    // Any exceptions that are throws will be put in the error position
    // of CanFail.
    def apply[A](thunk: => A): CanFail[Throwable, A] = {
      try success(thunk)
      catch { case e: Throwable => fail(e) }
    }
  }
  case class Success[+A, +B](b: B) extends CanFail[A, B]
  case class Failure[+A, +B](a: A) extends CanFail[A, B]
}
