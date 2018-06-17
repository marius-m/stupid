package lt.markmerkk.durak

import lt.markmerkk.Mocks
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleDefendingActionsFilter
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PossibleDefendingActionsFilterDefendableCardsSpek : Spek({
    val possibleDefendingActionsFilter = PossibleDefendingActionsFilter()

    given("no cards at the table") {
        on("nothing to defend against") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.SPADE, CardRank.ACE),
                            Card(CardSuite.SPADE, CardRank.KING),
                            Card(CardSuite.SPADE, CardRank.QUEEN),
                            Card(CardSuite.SPADE, CardRank.JACK)
                    ),
                    playingTable = Mocks.createPlayingTable()
            )

            // Assert
            it("nothing to defend against") {
                assertThat(resultActions).isEmpty()
            }
        }
    }

    given("one undefended card on table") {
        on("valid options to defend") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.SPADE, CardRank.ACE),
                            Card(CardSuite.SPADE, CardRank.KING),
                            Card(CardSuite.SPADE, CardRank.QUEEN),
                            Card(CardSuite.SPADE, CardRank.JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.TEN),
                                            defendingCard = null
                                    )
                            )
                    )
            )

            it("multiple variations to defend") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(Card(CardSuite.SPADE, CardRank.ACE)),
                        ActionThrowInCard(Card(CardSuite.SPADE, CardRank.KING)),
                        ActionThrowInCard(Card(CardSuite.SPADE, CardRank.QUEEN)),
                        ActionThrowInCard(Card(CardSuite.SPADE, CardRank.JACK))
                )
            }
        }

        on("only multiple actions higher") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.SPADE, CardRank.ACE),
                            Card(CardSuite.SPADE, CardRank.KING),
                            Card(CardSuite.SPADE, CardRank.JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.QUEEN),
                                            defendingCard = null
                                    )
                            )
                    )
            )

            it("multiple variations to defend") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(Card(CardSuite.SPADE, CardRank.ACE)),
                        ActionThrowInCard(Card(CardSuite.SPADE, CardRank.KING))
                )
            }
        }
    }
})

