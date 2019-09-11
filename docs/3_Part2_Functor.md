# Functors

## Introduction

> Functors are useful when dealing with **one** effectful value

You may not be familiar with the term `Functor`, but if you are a proficient Scala programmer you are almost
certainly familiar with the concept. `Functor` essentially expresses the ability to transform a wrapped value
using the `map` combinator e.g. `List.map`, `Option.map`, `Either.map`, `Future.map` etc.

For example

```scala
Some(1).map(_ + 1) // Some(2)
List(1, 2, 3).map(_ + 1) // List(2, 3, 4)
```

![functor](./functor.png)

The definition of `Functor` looks like this. Note that this is a **typeclass** that acts on a **type constructor** `F[_]`
where `F[_]` will be `Maybe` or `CanFail` in this workshop.

```scala
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(fn: A => B): F[B]
}
```

## Read The Code

- In [`functors.scala`](../src/main/scala/part2/functors.scala) the functor trait has been defined.

- Notice that an `implicit class` has also been defined, this allows the `map` function from `Functor` to be called 
  with dot syntax. This is following the same pattern we used when implementing the `Monoid` typeclass.
  
- Notice that the way `maybeFunctor` and `canFailFunctor` are declared is slightly different. One is a `val` and the other
  is a `def` that takes a type parameter. This is because the `Maybe` data type has only one type parameter, `Maybe[A]`, 
  whereas the `CanFail` data type has two type parameters, `CanFail[E, A]`. Referring back to our definition of `Functor`, 
  we can see that the higher kinded type parameter only matches type constructors with one type parameter, but we have two. 
  So, to satisfy the signature of `Functor` we need to fix one of the parameters, so that there is only one "hole" left to fill.
  
  So, we want to say that `F[A] => CanFail[E, A]` where `E` is fixed and `A` is a parameter. We can express that with the 
  `?` syntax from a compiler plugin called [kind-projector](https://github.com/typelevel/kind-projector) that conveniently 
  solves this problem. e.g. `CanFail[E, ?]`

## Exercises

**Exercise 1** &ndash; Maybe functor
   - Implement a `Functor` instance for `Maybe`. 
   - Run `FunctorLaws` using `sbt 'testOnly *FunctorLaws'` to check your implementation. 
     Note, there will still be failing tests for the `CanFail` functor at this stage.
  
**Exercise 2** &ndash; CanFail functor
   - Implement a `Functor` instance for `CanFail`
   - Run `FunctorLaws` using `sbt 'testOnly *FunctorLaws'` to check your implementation. 
     All the tests should pass now.
   
**Exercise 3** &ndash; Use functor instances
   - Use the functor instance for CanFail to define the `greeting` function
     This function should use `getName` to lookup the users name, and transform
     it into a greeting of the format `Hello $name, and welcome to functor town`
   - Run `FunctorSpec` using `sbt 'testOnly *FunctorSpec'` to check your implementation
   