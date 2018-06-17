package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PlayingTableFilterRanksOnTableSpek: Spek({
    given("filter works properly") {
        on("empty table") {
            val resultRanks = PlayingTable(
                    cards = emptyList()
            ).filterRanksOnTable()

            it("nothing to filter") {
                assertThat(resultRanks).isEmpty()
            }
        }

        on("one card on table") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.ACE),
                                    defendingCard = null
                            )
                    )
            ).filterRanksOnTable()

            it("has valid ranks") {
                assertThat(resultRanks).containsExactlyInAnyOrder(CardRank.ACE)
            }
        }

        on("one defended card on table") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.KING),
                                    defendingCard = Card(CardSuite.SPADE, CardRank.ACE)
                            )
                    )
            ).filterRanksOnTable()

            it("has valid ranks") {
                assertThat(resultRanks).containsExactlyInAnyOrder(CardRank.ACE, CardRank.KING)
            }
        }


        on("more than one defended card on table") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                    defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                            ),
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.DIAMOND, CardRank.KING),
                                    defendingCard = Card(CardSuite.DIAMOND, CardRank.ACE)
                            )
                    )
            ).filterRanksOnTable()

            it("has valid ranks") {
                assertThat(resultRanks).containsExactlyInAnyOrder(
                        CardRank.ACE,
                        CardRank.KING,
                        CardRank.JACK
                )
            }
        }
    }
})

