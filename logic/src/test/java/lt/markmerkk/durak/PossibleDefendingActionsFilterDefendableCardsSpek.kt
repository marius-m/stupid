package lt.markmerkk.durak

import lt.markmerkk.Mocks
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleDefendingActionsFilter
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PossibleDefendingActionsFilterDefendableCardsSpek : Spek({
    val possibleDefendingActionsFilter = PossibleDefendingActionsFilter()
    val defendingPlayer = Player(name = "Marius")

    given("no cards at the table") {
        on("nothing to defend against") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, QUEEN),
                            Card(SPADE, JACK)
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
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, QUEEN),
                            Card(SPADE, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, TEN),
                                            defendingCard = null
                                    )
                            )
                    )
            )

            it("multiple variations to defend") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, ACE)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, KING)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, QUEEN)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, JACK))
                )
            }
        }

        on("multiple actions higher") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, QUEEN),
                                            defendingCard = null
                                    )
                            )
                    )
            )

            it("multiple variations to defend") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, ACE)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, KING))
                )
            }
        }

        on("multiple cards and trump card is higher") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, JACK),
                            Card(DIAMOND, TWO, isTrump = true)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, QUEEN),
                                            defendingCard = null
                                    )
                            )
                    )
            )

            it("multiple variations to defend") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, ACE)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, KING)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(DIAMOND, TWO, isTrump = true))
                )
            }
        }
        on("multiple cards and trump card is higher, on multiple undefended cards") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, JACK),
                            Card(DIAMOND, TWO, isTrump = true)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, QUEEN),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, NINE),
                                            defendingCard = null
                                    )
                            )
                    )
            )

            it("only first first undefended card can be thrown") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, ACE)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, KING)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(DIAMOND, TWO, isTrump = true))
                )
            }
        }
    }
})

