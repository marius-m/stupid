package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object CardFromStringSpek: Spek({
    val allCardValues = CardSuite.values().toList()
            .combine(CardRank.values().toList())
            .map { (suite, rank) -> Card(suite, rank) }

    given("card is translated properly") {
        on("invalid card") {
            val resultCard = Card.fromString("invalid_card")

            it("should not parse out card") {
                assertThat(resultCard).isNull()
            }
        }

        allCardValues.forEach { card ->
            on("valid $card translation") {
                val translatedCard = Card.fromString(card.valueAsString())
                it("should translate to $card properly") {
                    assertThat(translatedCard).isEqualTo(card)
                }
            }
        }

    }
})

