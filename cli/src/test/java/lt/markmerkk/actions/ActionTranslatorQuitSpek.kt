package lt.markmerkk.durak

import lt.markmerkk.actions.ActionTranslatorQuit
import lt.markmerkk.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.actions.system.ActionSystemQuit
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object ActionTranslatorQuitSpek: Spek({
    val player1 = Player(name = "Marius")
    val player2 = Player(name = "Enrika")
    val players = listOf(player1, player2)

    given("translator has valid players") {
        val translatorValidPlayers = ActionTranslatorQuit(players = players)

        on("system action quit with incorrect player") {
            val resultAction = translatorValidPlayers.translateAction("invalid_player quit")

            it("invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "invalid_player quit"))
            }
        }

        on("system action quit with correct player") {
            val resultAction = translatorValidPlayers.translateAction("Marius quit")

            it("should create quit action") {
                assertThat(resultAction).isEqualTo(ActionSystemQuit(actionIssuer = player1))
            }
        }
    }

    given("translator has no players") {
        val translatorNoPlayers = ActionTranslatorQuit(players = emptyList())

        on("invalid command") {
            val resultAction = translatorNoPlayers.translateAction("invalid_action")

            it("should return empty action") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate("invalid_action"))
            }
        }

        on("system action quit") {
            val resultAction = translatorNoPlayers.translateAction("quit")

            it("should create quit action") {
                assertThat(resultAction).isEqualTo(ActionSystemQuit())
            }
        }
    }
})

