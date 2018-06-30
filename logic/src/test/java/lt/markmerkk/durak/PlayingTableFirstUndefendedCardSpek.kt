package lt.markmerkk.durak

import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PlayingTableFirstUndefendedCardSpek: Spek({
    given("filter properly") {
        on("empty table") {
            val resultRanks = PlayingTable(
                    cards = emptyList()
            ).firstUndefendedCardOnTable()

            it("no first undefended card") {
                assertThat(resultRanks).isNull()
            }
        }
        on("only first undefended card") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(SPADE, TEN),
                                    defendingCard = null
                            )
                    )
            ).firstUndefendedCardOnTable()

            it("valid selection") {
                assertThat(resultRanks).isEqualTo(Card(SPADE, TEN))
            }
        }
        on("first defended pair") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(SPADE, TEN),
                                    defendingCard = Card(SPADE, JACK)
                            )
                    )
            ).firstUndefendedCardOnTable()

            it("no card selected") {
                assertThat(resultRanks).isNull()
            }
        }
        on("multiple play cards, two undefended") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(SPADE, TEN),
                                    defendingCard = null
                            ),
                            PlayingCardPair(
                                    attackingCard = Card(DIAMOND, TEN),
                                    defendingCard = null
                            )
                    )
            ).firstUndefendedCardOnTable()

            it("valid selection") {
                assertThat(resultRanks).isEqualTo(Card(SPADE, TEN))
            }
        }
        on("multiple play cards, second undefended") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(SPADE, TEN),
                                    defendingCard = Card(DIAMOND, QUEEN)
                            ),
                            PlayingCardPair(
                                    attackingCard = Card(DIAMOND, TEN),
                                    defendingCard = null
                            )
                    )
            ).firstUndefendedCardOnTable()

            it("valid selection") {
                assertThat(resultRanks).isEqualTo(Card(DIAMOND, TEN))
            }
        }
    }
})

