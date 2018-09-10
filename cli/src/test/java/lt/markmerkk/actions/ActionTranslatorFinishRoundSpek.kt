package lt.markmerkk.durak

import lt.markmerkk.actions.ActionTranslatorFinishRound
import lt.markmerkk.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.ActionFinishRound
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object ActionTranslatorFinishRoundSpek : Spek({

    given("translator has valid players") {
        val player1 = Player(name = "Marius")
        val player2 = Player(name = "Enrika")
        val translatorValidPlayers = ActionTranslatorFinishRound(players = listOf(player1, player2))
        on("valid action") {
            val resultAction = translatorValidPlayers.translateAction("Marius finish round")
            it("should be valid command") {
                assertThat(resultAction).isEqualTo(ActionFinishRound(actionIssuer = player1))
            }
        }
    }

    given("translator no players") {
        val translatorValidPlayers = ActionTranslatorFinishRound(players = emptyList())
        on("valid action") {
            val resultAction = translatorValidPlayers.translateAction("Marius take all")
            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius take all"))
            }
        }
    }

})

