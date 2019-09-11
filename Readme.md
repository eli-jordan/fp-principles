# Functional Programming Principles in Scala

## Introduction

Typeclasses are a pattern that is widespread in functional programming libraries such as [cats](https://typelevel.org/cats/) 
and [cats-effect](https://typelevel.org/cats-effect/). In part one of this workshop we will explore how the mechanics of typeclasses
work in Scala using the `Monoid` abstraction as an example. Then, in part two we will cover the patterns of composition defined by
the `Functor`, `Applicative` and `Monad` typeclasses.

In this workshop we will be building **everything** from first principles. We will not be using any functional programming
libraries and will only make minimal use of the scala standard library. By building these constructs from first principles
you will understand at a fundamental level how they work, and when they should be applied. 

After this workshop you should understand the mechanics of typeclasses, and the composition patterns expressed by `Functor`
`Applicative` and `Monad`. This will allow you to effectively make use of similar abstractions libraries such as 
[cats](https://typelevel.org/cats/) and provide the foundation to learn about other FP concepts independently.

## The Workshop

All the details of the exercises, including reference to the relevant sections of provided code
are linked below.

**Part 1** &ndash; Covers the mechanics of typeclasses. 
  - [Typeclasses](./docs/1_Part1_Typeclasses.md)
  
**Part 2** &ndash; Covers `Functor`, `Applicative` and `Monad`
  - [Introduction](./docs/2_Part2_Intro.md)
  - [Functor](./docs/3_Part2_Functor.md)
  - [Applicative](./docs/4_Part2_Applicative.md)
  - [Monad](./docs/5_Part2_Monad.md)
  - [Summary](./docs/6_Part2_Summary.md)
  
**Solutions** &ndash; I strongly encourage you to try to solve all the exercises without referring to the solutions.
   If you are stuck, please ask a question. However, there are solutions to all the exercises in the `solutions` branch
   that you can refer to if needed.
  