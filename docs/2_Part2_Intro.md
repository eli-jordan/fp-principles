# Part 2 - Introduction

## Effects

Most of the common typeclasses used in functional programming take a "higher-kinded" type parameter, sometimes referred 
to as a type constructor.

In scala a type constructor looks like this `F[_]` and is a type parameter that can represent any type that takes one 
type argument such as `List`, `Option`, `Either[E, ?]` and more.

The `F[_]` here can be thought of as an **effect** in which we compose functions. It adds additional semantics to the composition,
and those semantics are determined by the concrete instantiation of `F[_]`. Lets look at an illustrative example.

```scala
val asInt: String => Int = _.toInt
val inc: Int => Int = _ + 1

// This is normal function composition. However, toInt can throw an exception.
// Throwing an exception is a side effect, which we don't want in functional code.
// To avoid throwing an exception, we need to encode the fact that toInt can fail in the
// return type of our asInt function.
val valuePlusOne = asInt andThen inc

// Here we have encoded the fact that toInt can fail in the return type
// and we once again have pure functions. However, we can no longer use regular
// function composition to increment the result of calling asInt2, since it is wrapped
// in an Either value.
val asInt2: String => Either[Throwable, Int] = 
  s => Try(s.toInt).toEither

// Here we use the `map` combinator to apply our `inc` function
// in the context of `Either`. We are composing functions
// using the Either effect.
val valuePlusOne2: String => Either[Throwable, Int] = 
  s => asInt2(s).map(inc) 
```

In the above example `Either` is the effect in which we are composing functions. The additional
semantics it encodes are the ability to short circuit on failure, somewhat similar to exceptions.

These additional semantics are defined in the implementation of `map` for `Either`. If the `Either` 
is in the failed state, then the function passed to `map` is never applied, and the error is propagated.
If it is in the successful state, then the function is applied to the value and wrapped in an `Either`
again.

There are many such effects that we can use to encode different composition semantics.

- `Option` encodes the semantics of a value possibly not being defined
- `Either` and `Try` encode short circuiting on failure.
- `Reader` encodes the ability to inspect global read-only data.
- `State` encodes the ability to simulate mutable state, but in a compositional and purely functional way.
- `IO` encodes the ability to perform IO operations in a pure way.
- ... there are many more

While each of these data types express different compositional semantics (definedness, failure, configuration, state etc)
the combinators that are used to compose them all follow the same structure. It is these **patterns of composition** that the
commonly used typeclasses define.

In the remainder of this workshop we will be looking at three of the most commonly used typeclasses, `Functor`, `Applicative`
and `Monad`. We will look at the patterns of composition they describe, and how those patterns are defined for two concrete
effects. In particular `Maybe` and `CanFail`. `Maybe` is equivalent to `scala.Option` and `CanFail` is equivalent to `scala.util.Either`.
They are defined in [`data.scala`](../src/main/scala/part2/data.scala)

This should give you a solid foundation in the common patterns of composition in functional programming, and the ability
to learn more independently.
