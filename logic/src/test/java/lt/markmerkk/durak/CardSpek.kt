package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object CardSpek: Spek({
    given("deck initialized") {
        val cards = Card.generateDeck()

        on("init") {
            it("should have size 52") {
                assertThat(cards.size).isEqualTo(52)
            }

            val onlyClubs = cards.filter { it.suite == CardSuite.CLUB }
            it("should contain 13 clubs") {
                assertThat(onlyClubs.size).isEqualTo(13)
            }

            val onlyDiamonds = cards.filter { it.suite == CardSuite.DIAMOND }
            it("should contain 13 diamonds") {
                assertThat(onlyDiamonds.size).isEqualTo(13)
            }

            val onlyHearts = cards.filter { it.suite == CardSuite.HEART }
            it("should contain 13 hearts") {
                assertThat(onlyHearts.size).isEqualTo(13)
            }

            val onlySpades = cards.filter { it.suite == CardSuite.SPADE }
            it("should contain 13 spades") {
                assertThat(onlySpades.size).isEqualTo(13)
            }
        }
    }
})

