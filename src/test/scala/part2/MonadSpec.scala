package part2

import org.scalatest.{FlatSpec, MustMatchers}
import part2.data._
import part2.monads._

class MonadSpec extends FlatSpec with MustMatchers {
  behavior of "ifM"

  it should "Use the `ifTrue` effect when the condition results in `true`" in {
    val cond    = Maybe.defined(true)
    val ifTrue  = Maybe.defined("it was true")
    val ifFalse = Maybe.defined("it was false")
    ifM(cond)(ifTrue, ifFalse) mustBe Defined("it was true")
  }

  it should "Use the `ifFalse` effect when the condition results in `false`" in {
    val cond    = Maybe.defined(false)
    val ifTrue  = Maybe.defined("it was true")
    val ifFalse = Maybe.defined("it was false")
    ifM(cond)(ifTrue, ifFalse) mustBe Defined("it was false")
  }

  it should "apply the semantics of the condition" in {
    val cond    = Maybe.undefined[Boolean]
    val ifTrue  = Maybe.defined("it was true")
    val ifFalse = Maybe.defined("it was false")
    ifM(cond)(ifTrue, ifFalse) mustBe Undefined
  }
}
