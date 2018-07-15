package lt.markmerkk.actions.system

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object ActionIllegalCannotTranslateTest: Spek({
    given("object equality works properly") {

        on("same instance") {
            val action1 = ActionIllegalCannotTranslate()
            val action2 = ActionIllegalCannotTranslate()
            val resultEquality = action1.equals(action2)
            it("should be equal") {
                assertThat(resultEquality).isTrue()
            }
        }

        on("different inputs") {
            val action1 = ActionIllegalCannotTranslate(inputAsString = "input1")
            val action2 = ActionIllegalCannotTranslate(inputAsString = "input2")
            val resultEquality = action1.equals(action2)
            it("should be equal") {
                assertThat(resultEquality).isFalse()
            }
        }

        on("different actions") {
            val action1 = ActionIllegalCannotTranslate(inputAsString = "input1")
            val action2 = ActionSystemQuit()
            val resultEquality = action1.equals(action2)
            it("should not be equal") {
                assertThat(resultEquality).isFalse()
            }
        }

        on("no action") {
            val action1: ActionIllegalCannotTranslate = ActionIllegalCannotTranslate(inputAsString = "input1")
            val action2: ActionIllegalCannotTranslate? = null
            val resultEquality = action1.equals(action2)
            it("should not be equal") {
                assertThat(resultEquality).isFalse()
            }
        }
    }
})