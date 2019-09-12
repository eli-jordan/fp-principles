package part2

import part2.data._
import part2.functors.{CanFailFunctor, Functor, MaybeFunctor}

object applicatives {

  // The abstract definition of Applicative
  trait Applicative[F[_]] extends Functor[F] {
    def pure[A](a: A): F[A]
    def zip[A, B](fa: F[A], fb: F[B]): F[(A, B)]

    // ap is the traditional primitive operation for Applicative,
    // but here we have defined it in terms of zip and map
    def ap[A, B](ff: F[A => B])(fa: F[A]): F[B] =
      map(zip(ff, fa)) { case (f, a) => f(a) }
  }

  // This is the implicit class that defines the dot syntax for applicative.
  //
  // Note: We haven't defined dot syntax for the `pure` function, but this is also
  //       possible.
  implicit class ApplicativeOps[F[_], A](fa: F[A]) {
    def zip[B](fb: F[B])(implicit A: Applicative[F]): F[(A, B)] = A.zip(fa, fb)
  }

  // Exercise 1 - Implement an applicative instance for Maybe
  class MaybeApplicative extends MaybeFunctor with Applicative[Maybe] {
    override def pure[A](a: A): Maybe[A] = Defined(a)
    override def zip[A, B](fa: Maybe[A], fb: Maybe[B]): Maybe[(A, B)] = (fa, fb) match {
      case (Defined(a), Defined(b)) => Defined((a, b))
      case _                        => Undefined
    }
  }

  // Exercise 2 - Implement an applicative instance for CanFail
  class CanFailApplicative[E] extends CanFailFunctor[E] with Applicative[CanFail[E, ?]] {
    override def pure[A](a: A): CanFail[E, A] = Success(a)
    override def zip[A, B](fa: CanFail[E, A], fb: CanFail[E, B]): CanFail[E, (A, B)] = (fa, fb) match {
      case (Success(a), Success(b)) => Success((a, b))
      case (Failure(e), _)        => Failure(e)
      case (_, Failure(e))        => Failure(e)
    }
  }

  object Applicative {

    // This is the "summoning" method for Applicative
    def apply[F[_]](implicit ev: Applicative[F]): Applicative[F] = ev

    // Defining these values as implicits makes them typeclass instances
    implicit val maybeApplicative: Applicative[Maybe] = new MaybeApplicative
    implicit def canFailApplicative[E]: Applicative[CanFail[E, ?]] = new CanFailApplicative[E]
  }

  import functors._

  // Exercise 3 - Implement sequence
  def sequence[F[_]: Applicative, A](fas: List[F[A]]): F[List[A]] = {
    fas.foldRight(Applicative[F].pure(List.empty[A])) { (fa: F[A], acc: F[List[A]]) =>
      acc.zip(fa).map { case (as, a) => a :: as }
    }
  }

  // Exercise 4 - Convert a list of strings to Int using the toInt helper
  // Tip: Use map and sequence
  def asInts(xs: List[String]): CanFail[Throwable, List[Int]] = {
    def toInt(a: String): CanFail[Throwable, Int] = CanFail(a.toInt)
    val maybeInts: List[CanFail[Throwable, Int]] = xs.map(toInt)
    sequence(maybeInts)
  }
}
