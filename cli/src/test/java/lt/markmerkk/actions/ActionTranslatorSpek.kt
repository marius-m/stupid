package lt.markmerkk.durak

import lt.markmerkk.actions.ActionTranslator
import lt.markmerkk.actions.system.ActionEmpty
import lt.markmerkk.actions.system.ActionSystemQuit
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object ActionTranslatorSpek: Spek({
    given("translator initializes") {
        val translator = ActionTranslator()

        on("invalid command") {
            val resultAction = translator.translateAction("invalid_action")
            it("it should return empty action") {
                assertThat(resultAction).isInstanceOf(ActionEmpty::class.javaObjectType)
            }
        }
        on("system action quit") {
            val resultAction = translator.translateAction("quit")
            it("it should create quit action") {
                assertThat(resultAction).isInstanceOf(ActionSystemQuit::class.javaObjectType)
            }
        }
    }
})

