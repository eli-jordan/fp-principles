# Monad

## Introduction

> Monads are useful when dealing with **multiple dependent** effectful values

We have just seen how to compose multiple effects that have no dependencies between them. However, its quite common
to want the ability for the result of evaluating one effect to determine what effect will be run next. For example,
I want to read a configuration file from disk and then based on the contents of the file determine what URL to send
a request to. These operation have a strict data dependency, I **MUST** first read the config file, **THEN**
issue the request using the specified URL.

This data dependency is expressed in the world of effectful values using the `flatMap` function. If you are familiar
with `scala.concurrent.Future` you are likely familiar with the shape of the `flatMap` function.

![monad](./monad.png)

The definition of `Monad` looks like this

```scala
trait Monad[F[_]] extends Applicative[F] {
   def flatMap[A, B](fa: F[A])(fn: A => F[B]): F[B]
}
```

The data dependency can clearly be seen in the signature of this function. We start with an `F[A]` but have a function that
takes an `A` that is not wrapped in an effect. So, we need some way of running the `fa` effect to pull out an `A` before
we can apply the function. We could try using `map` (e.g. `fa.map(fn): F[F[B]]`) but then we have two layers of `F` and
no way to flatten them.

Note: That any data type that has the `map` and `flatMap` functions defined can be used in a for-comprehension. Here, we
inherit `map` from `Functor` and the `Monad` typeclass defined `flatMap`. So, if our type has a monad instance we can use
it in for-comprehensions, and make the sequential nature of monadic composition more apparent.

## Read The Code

- In [`monads.scala`](../src/main/scala/part2/monads.scala) the `Monad` trait has been defined.

- Similar to the previous section, an `implicit class` has been defined to provide dot syntax.

- Notice that `Monad` extends `Applicative`. This implies that `Monad` is more powerful than `Applicative`. 

- Notice that we are able to implement all the functions from `Functor` and `Applicative` based on `flatMap` and `pure`.
  So, the primitive operations required to implement a `Monad` are `pure` and `flatMap` and the rest of the functions
  we have been using can be derived from them.

## Exercises

**Exercise 1** &ndash; Maybe monad
   - Implement a `Monad` instance for `Maybe`
   - Run `MonadLaws` using `sbt 'testOnly *MonadLaws'` to check your implementation. 
     Note there will still be some failures related to `CanFail` at this stage.
   
**Exercise 2** &ndash; CanFail monad
   - Implement a `Monad` instance for `CanFail`
   - Run `MonadLaws` using `sbt 'testOnly *MonadLaws'` to check your implementation. 
     All tests should pass at this point.
   
**Exercise 3** &ndash; Use `Monad`
   - Implement the `ifM` function
   - Run `MonadSpec` using `sbt 'testOnly *MonadSpec'` to check your implementation.