package part2
import part2.applicatives._
import part2.data._

object monads {

  // The abstract definition of Monad
  trait Monad[F[_]] extends Applicative[F] {
    def flatMap[A, B](fa: F[A])(fn: A => F[B]): F[B]

    def flatten[A](ffa: F[F[A]]): F[A] =
      flatMap(ffa)(identity)

    override def map[A, B](fa: F[A])(fn: A => B): F[B] =
      flatMap(fa)(a => pure(fn(a)))

    override def zip[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
      flatMap(fa)(a => flatMap(fb)(b => pure((a, b))))
  }

  // This is the implicit class that defines the dot syntax for monad.
  implicit class MonadOps[F[_]: Monad, A](fa: F[A]) {
    def flatMap[B](fn: A => F[B]): F[B] = Monad[F].flatMap(fa)(fn)
  }

  // Exercise 1 - Implement a Monad instance for Maybe
  class MaybeMonad extends MaybeApplicative with Monad[Maybe] {
    override def flatMap[A, B](fa: Maybe[A])(fn: A => Maybe[B]): Maybe[B] = ???
  }

  // Exercise 2 - Implement  a Monad instance for CanFail
  class CanFailMonad[E] extends CanFailApplicative[E] with Monad[CanFail[E, ?]] {
    override def flatMap[A, B](fa: CanFail[E, A])(fn: A => CanFail[E, B]): CanFail[E, B] = ???
  }

  object Monad {

    // This is the "summoning" method for Monad
    def apply[F[_]](implicit ev: Monad[F]): Monad[F] = ev

    // Defining these values as implicits makes them typeclass instances
    implicit val maybeMonad: Monad[Maybe] = new MaybeMonad
    implicit def canFailMonad[E]: Monad[CanFail[E, ?]] = new CanFailMonad[E]
  }

  // Exercise 3 - Implement ifM
  //
  // This function should fist run the fa effect, and inspect the boolean value it holds.
  // If the boolean is true, then the ifTrue effect should be run, if its false the ifFalse
  // effect should be run.
  def ifM[F[_], B](fa: F[Boolean])(ifTrue: => F[B], ifFalse: => F[B])(implicit F: Monad[F]): F[B] = ???
}
