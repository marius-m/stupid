package lt.markmerkk.actions

import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.ActionTranslatorTakeAll
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.ActionTakeAllCards
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object ActionTranslatorTakeAllSpek : Spek({

    given("translator has valid players") {
        val player1 = Player(name = "Marius")
        val player2 = Player(name = "Enrika")
        val translatorValidPlayers = ActionTranslatorTakeAll(players = listOf(player1, player2))
        on("valid take all action") {
            val resultAction = translatorValidPlayers.translateAction("Marius take all")

            it("should be valid take all command") {
                assertThat(resultAction).isEqualTo(ActionTakeAllCards(actionIssuer = player1))
            }
        }

        on("unfinished action") {
            val resultAction = translatorValidPlayers.translateAction("Marius take")

            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius take"))
            }
        }
    }

    given("translator no players") {
        val translatorValidPlayers = ActionTranslatorTakeAll(players = emptyList())

        on("valid action") {
            val resultAction = translatorValidPlayers.translateAction("Marius take all")

            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius take all"))
            }
        }
    }

})

