package lt.markmerkk.durak

import lt.markmerkk.actions.ActionTranslatorThrowCards
import lt.markmerkk.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.ActionThrowInCard
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object ActionTranslatorCardsSpek : Spek({

    given("translator has valid players") {
        val player1 = Player(name = "Marius")
        val player2 = Player(name = "Enrika")
        val translatorValidPlayers = ActionTranslatorThrowCards(players = listOf(player1, player2))
        val allCardValues = CardSuite.values().toList()
                .combine(CardRank.values().toList())
                .map { (suite, rank) -> Card(suite, rank) }
        allCardValues.forEach { card ->
            on("valid throw $card action") {
                val resultAction = translatorValidPlayers.translateAction("Marius throw ${card.valueAsString()}")

                it("should be valid throw command") {
                    assertThat(resultAction).isEqualTo(ActionThrowInCard(actionIssuer = player1, thrownCard = card))
                }
            }
        }

        on("unfinished action") {
            val resultAction = translatorValidPlayers.translateAction("Marius throw ")

            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius throw "))
            }
        }

    }

    given("translator no players") {
        val translatorValidPlayers = ActionTranslatorThrowCards(players = emptyList())

        on("valid action") {
            val resultAction = translatorValidPlayers.translateAction("Marius throw D9")

            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius throw D9"))
            }
        }
    }

})

