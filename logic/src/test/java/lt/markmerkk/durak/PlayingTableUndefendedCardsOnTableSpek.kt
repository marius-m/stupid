package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PlayingTableUndefendedCardsOnTableSpek: Spek({
    given("filter works properly") {
        on("empty table") {
            val resultRanks = PlayingTable(
                    cards = emptyList()
            ).undefendedCardsOnTable()

            it("nothing to filter") {
                assertThat(resultRanks).isEmpty()
            }
        }

        on("undefended cards") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.ACE),
                                    defendingCard = null
                            ),
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.KING),
                                    defendingCard = null
                            )
                    )
            ).undefendedCardsOnTable()

            it("filters undefended cards") {
                assertThat(resultRanks).containsExactlyInAnyOrder(
                        Card(CardSuite.SPADE, CardRank.ACE),
                        Card(CardSuite.SPADE, CardRank.KING)
                )
            }
        }

        on("has already defended cards") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.ACE),
                                    defendingCard = null
                            ),
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.KING),
                                    defendingCard = Card(CardSuite.DIAMOND, CardRank.NINE)
                            )
                    )
            ).undefendedCardsOnTable()

            it("filters only undefended cards") {
                assertThat(resultRanks).containsExactlyInAnyOrder(
                        Card(CardSuite.SPADE, CardRank.ACE)
                )
            }
        }
    }
})

