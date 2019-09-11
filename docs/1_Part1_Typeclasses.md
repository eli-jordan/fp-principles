# Typeclasses

## Introduction

Polymorphism is the ability (in programming) to present the same interface for differing underlying types.
There are several different types of polymorphism that you are likely familiar with.

- **Subtype polymorphism** &ndash; Allows a subtype to be provided where a super type is expected. 

   ```scala
   def sum(s: Seq[Int]): Int = s.foldLeft(0)(_ + _) 
   
   // We can pass any type that is a subtype of Seq[Int]
   sum(List(1, 2, 3))
   sum(Vector(1, 2, 3))
   ```

- **Parametric polymorphism** &ndash; Allows methods and functions to abstract over type using type variables.

   ```scala
   // We are able to operate on a list containing any type A
   def length[A](l: List[A]): Int = l match {
     case x :: xs => 1 + length(xs)
     case Nil => 0
   }
   ```
   
There are also other kinds of polymorphism. We are interested in **adhoc polymorphism** in particular, since typeclasses
are a way to express adhoc polymorphism. 

You can think of adhoc polymorphism as blend of the properties of subtype and parametric polymorphism. Subtype polymorphism dispatches a 
method call based on the runtime type of the reference. Parametric polymorphism (at least in its simplest form) doesn't allow dispatch on the generic type.

Adhoc polymorphism uses parametric polymorphism, but rather than allowing any type (as in `def length[A](as: List[A]): Int = ???`)
it is possible to place additional restrictions on what is an allowable type, and in doing so allow more operations to be
performed on values of the generic type. Since this behavior is selected based solely on the generic type, the 
dispatch is determined at compile time, not runtime like with subtype polymorphism. 

Additionally, since adhoc-polymorphism can be used to enrich any generic type, it can also enrich types that you do not control.
For example the scala standard library. 

## Read The Code

- Each of the examples have some code to get you started in [`typeclasses.scala`](../src/main/scala/part1/typeclasses.scala)

- There is a very concrete `combine` function defined for you. We will be gradually making this function more generic.

- Test cases are defined in [`TypeclassesSpec.scala`](../src/test/scala/part1/TypeclassesSpec.scala)

## Exercises

In this section we will use a simple type class to write a generic `combine` function. Through that process we will see
how to express typeclasses in scala and how to make use of them to write generic functions. Lets get started!

The goal we are working towards is writing a function with the following signature

```scala
def combine[A: Monoid, F[_]: FoldLeft](as: F[A]): A = ???
```

That can be used like this

```scala
List(1, 2, 3).combine // 6
List("1", "2", "3").combine // "123"
Tree(Leaf(1), Leaf(2)).combine // 3
```

> `A: Monoid` in the above snippet is called a [context bound](https://docs.scala-lang.org/tutorials/FAQ/context-bounds.html)
> and simply a way to express that there must be a `Monoid[A]` implicit available to call the function.


- **Exercise 1** &ndash; Looking at the concrete `combine1` function, we can see that there are two things we need to
   know to implement it. A 'zero' element as well as a function to combine two elements of the same type and produce 
   another 'combined' element of the same type. This pattern is expressed using the `Monoid` trait, which is defined 
   for you.
   
   a. Implement `combine2` and run the associated test in `TypeclassesSpec`
      Note: Use `sbt 'testOnly -- -n typeclasses.combine2'` to run the tests
      
   b. How would we need to change 'combine2' so that it is no longer specific to Int?
       - Change your implementation so that its not specific to Int
       
- **Exercise 2** &ndash; In exercise 1 we abstracted over the components of a 'foldLeft', so that
  we could write the combine function without being specific to Int. However, we haven't yet
  used it with any other type. Add a test to `TypeclassesSpec` that uses the abstracted combine2 
  with List[String]. Note there is a stub test defined ready for you to implement.
  
  Run the tests using `sbt 'testOnly -- -n typeclasses.combine2'`

- **Exercise 3** &ndash; We have now written a combine function that works for any `List[A]`, where we have implemented a
  `Monoid[A]` for the element type. However, we still need to explicitly pass the `Monoid` implementation.
 
  Implement `combine3` so that it can be called like this
  ```scala
  combine3(List(1, 2, 3)) // 6
  combine3(List("1", "2", "3")) // "123"
  ```  
  
  Run the relevant tests in `TypeclassesSpec` using `sbt 'testOnly -- -n typeclasses.combine3'`
  
- **Exercise 4** &ndash; At this point we are quite close to what we were aiming to achieve. We can call `combine3(List(1, 2, 3))` 
   but we want to be able to treat our combine function like a regular method e.g. `List(1, 2, 3).combine`.
   This can be achieved using an [`implicit class`](https://docs.scala-lang.org/overviews/core/implicit-classes.html).
 
   Modify the `MonoidOps4` implicit class so that we can call `combine4` as if it was a method on list.
   
   Run the relevant tests in `TypeclassesSpec` using `sbt 'testOnly -- -n typeclasses.combine4'`
   
- **Exercise 5 (optional)** &ndash; At this point, we have covered the core mechanics of typeclasses in scala.
    - Typeclasses are defined as a trait with one or more type parameters. We defined the 'Monoid' typeclass.
    - Implementations of typeclasses are often referred to as "instances" of the typeclass at a given type.
    - Typeclass instances are defined using implicit values that implement the typeclass trait. We implemented
      monoid instances for Int and String.
    - We can add additional behaviour to a type parameter, by requiring that a typeclass instance is available.
      We saw this when implementing combine3, when an implicit `Monoid[A]` was declared.
    - Finally, we can use type classes to implement generic extension methods. This is sometimes called "syntax"
 
  However, if you recall our original goal, we wanted to be able abstract away from List too.
 
  Implement `combine5` using the `FoldLeft` and `Monoid` typeclasses.
  
  Run the relevant tests in `TypeclassesSpec` using `sbt 'testOnly -- -n typeclasses.combine5'`
  