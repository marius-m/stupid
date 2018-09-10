package lt.markmerkk.durak

import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.DIAMOND
import lt.markmerkk.durak.CardSuite.SPADE
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PlayingTableFirstUndefendedCardSpek: Spek({

    val playingTable = PlayingTable(emptyList())

    given("filter properly") {
        on("empty table") {
            playingTable.cards = emptyList()

            val resultRanks = playingTable.firstUndefendedCardOnTable()
            it("no first undefended card") {
                assertThat(resultRanks).isNull()
            }
        }
        on("only first undefended card") {
            playingTable.cards = listOf(PlayingCardPair(
                    attackingCard = Card(SPADE, TEN),
                    defendingCard = null
            ))
            val resultRanks = playingTable.firstUndefendedCardOnTable()

            it("valid selection") {
                assertThat(resultRanks).isEqualTo(Card(SPADE, TEN))
            }
        }
        on("first defended pair") {
            playingTable.cards = listOf(PlayingCardPair(
                    attackingCard = Card(SPADE, TEN),
                    defendingCard = Card(SPADE, JACK)
            ))
            val resultRanks = playingTable.firstUndefendedCardOnTable()

            it("no card selected") {
                assertThat(resultRanks).isNull()
            }
        }
        on("multiple play cards, two undefended") {
            playingTable.cards = listOf(
                    PlayingCardPair(
                            attackingCard = Card(SPADE, TEN),
                            defendingCard = null
                    ),
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, TEN),
                            defendingCard = null
                    )
            )
            val resultRanks = playingTable.firstUndefendedCardOnTable()

            it("valid selection") {
                assertThat(resultRanks).isEqualTo(Card(SPADE, TEN))
            }
        }
        on("multiple play cards, second undefended") {
            playingTable.cards = listOf(
                    PlayingCardPair(
                            attackingCard = Card(SPADE, TEN),
                            defendingCard = Card(DIAMOND, QUEEN)
                    ),
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, TEN),
                            defendingCard = null
                    )
            )
            val resultRanks = playingTable.firstUndefendedCardOnTable()

            it("valid selection") {
                assertThat(resultRanks).isEqualTo(Card(DIAMOND, TEN))
            }
        }
    }
})

