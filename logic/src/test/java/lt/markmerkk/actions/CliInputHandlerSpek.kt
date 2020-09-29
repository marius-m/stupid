package lt.markmerkk.actions

import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.CliInputHandler
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.system.ActionIllegalMultipleActions
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object CliInputHandlerSpek : Spek({
    val players = listOf(Player(name = "Marius"), Player(name = "Enrika"))
    given("input handler works properly") {

        on("empty translator") {
            val inputHandler = CliInputHandler(actionTranslators = emptyList())
            it("should return empty action") {
                val resultAction = inputHandler.handleInput("valid_input")
                assertThat(resultAction).isInstanceOf(ActionIllegalCannotTranslate::class.java)
            }
        }

        on("proper translator convert") {
            val inputHandler = CliInputHandler(
                    actionTranslators = listOf(
                            FakeTranslatorValid(FakeAction1())
                    )
            )
            it("should return valid action") {
                val resultAction = inputHandler.handleInput("valid_input")
                assertThat(resultAction).isInstanceOf(FakeAction1::class.java)
            }
        }

        on("on multiple valid translations") {
            val inputHandler = CliInputHandler(
                    actionTranslators = listOf(
                            FakeTranslatorValid(FakeAction1()),
                            FakeTranslatorValid(FakeAction2())
                    )
            )
            it("should return as invalidly translated") {
                val resultAction = inputHandler.handleInput("valid_input")
                assertThat(resultAction).isInstanceOf(ActionIllegalMultipleActions::class.java)
            }
        }
    }
})

