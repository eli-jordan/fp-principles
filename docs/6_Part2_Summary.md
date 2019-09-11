# Part 2 - Summary

In functional programming we want all functions to be pure, which means they don't throw exceptions
or return null or use mutable state etc. This means we need to use effectful values, i.e. compositional values
that allow the same semantics as the imperative style of programming, but in a pure fashion. In this workshop
we have looked at `Maybe` which is a pure and compositional replacement for null and we have looked at `CanFail` 
which is a pure and compositional replacement for exceptions. We have also looked at the patterns that can be 
used to compose these effectful values.

- `Functor`'s `map` function can be used to transform a **one effectful value**
- `Applicative`'s `zip` function can be used to compose **multiple independent effectful values**
- `Monad`s `flatMap` function can be used to compose **multiple dependent effectful values**

We have just looked at two effect types, `Maybe` and `CanFail`, however there are many more that can be used
to express different composition semantics. However, the patterns of composition are expressed using the same 
typeclasses we have just leaned about, and their implementation is bound by laws. So, since we have now learned these
patterns of composition we automatically know how to effectively work with a whole class effectful data-types.
The is a massive win when it comes to learning new libraries that are written in a functional style. If you already
understand the composition patterns that are used, you already understand most of the library.

This is the essence of **compositionality** - If you understand the atomic units (effectful values in our case)
and the patterns of composition of those units (e.g. map, zip and flatMap) then you understand the whole (e.g. a program). 
In other words there are no interactions or semantics other than what are defined by our atomic units and 
they way they are composed.

Some examples of other effects we can combine using the patterns we have already learned

- [`State`](https://typelevel.org/cats/datatypes/state.html) represents a purely functional stateful computation, that can be composed 
   using all the functions we have already seen.
- [`Reader`](https://typelevel.org/cats/datatypes/kleisli.html) represents the ability to have thread read-only state through
  a purely functional computation.
- [`IO`](https://typelevel.org/cats-effect/datatypes/io.html) describes purely functional IO operations.
- and many more

In this workshop we implemented the data types and typeclasses from scratch, however, in practice we would use a library
such as [cats](https://typelevel.org/cats/) which provides these and more. Cats also provides typeclass instances for 
many types from the scala standard library such as `List`, `Option` and `Either`. This means you get many useful
combinators for these types such as `.sequence` which we implemented in the `Appliicative` section.

In the next workshop, we will use the typeclasses and functions from the [cats](https://typelevel.org/cats/) library that 
are equivalent to what we have implemented today to create a simple reading list application built using the 
[tagless final](http://okmij.org/ftp/tagless-final/index.html) style.


## References

- Constraints Liberate, Liberties Constrain - https://www.youtube.com/watch?v=GqmsQeSzMdw&feature=share
- Using `Functor`, `Applicative` and `Monad` for binary codecs - https://mpilquist.github.io/blog/2015/08/14/sbtb/
- Functional Programming with Effects - https://www.youtube.com/watch?v=po3wmq4S15A
- Applicative programming with effects - http://www.staff.city.ac.uk/~ross/papers/Applicative.pdf