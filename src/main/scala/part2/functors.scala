package part2

import part2.data._

object functors {

  // The abstract definition of functor
  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(fn: A => B): F[B]
  }

  // This is the implicit class that defines the dot syntax for functor.
  implicit class FunctorOps[F[_], A](fa: F[A]) {
    def map[B](fn: A => B)(implicit F: Functor[F]): F[B] = F.map(fa)(fn)
  }

  // Exercise 1 - Implement a functor instance for Maybe
  class MaybeFunctor extends Functor[Maybe] {
    override def map[A, B](fa: Maybe[A])(fn: A => B): Maybe[B] = fa match {
      case Defined(a) => Defined(fn(a))
      case Undefined  => Undefined
    }
  }

  // Exercise 2 - Implement a functor instance for CanFail
  class CanFailFunctor[E] extends Functor[CanFail[E, ?]] {
    override def map[A, B](fa: CanFail[E, A])(fn: A => B): CanFail[E, B] = fa match {
      case Failure(e) => Failure(e)
      case Success(a) => Success(fn(a))
    }
  }

  object Functor {

    // This is the "summoning" method for Functor
    def apply[F[_]](implicit ev: Functor[F]): Functor[F] = ev

    // Defining these values as implicits makes them typeclass instances
    implicit val maybeFunctor: Functor[Maybe]              = new MaybeFunctor
    implicit def canFailFunctor[E]: Functor[CanFail[E, ?]] = new CanFailFunctor[E]
  }

  // Exercise 3: Use the functor instance for CanFail to define a simple greeting.
  //
  // This function should use getName to lookup retrieve the users name, and transform
  // into a greeting of the format 'Hello $name, and welcome to functor town'
  def greeting: CanFail[Throwable, String] =
    getName.map { name => s"Hello $name, and welcome to functor town" }

  def getName: CanFail[Throwable, String] =
    CanFail(sys.props.get("name").get)
}
