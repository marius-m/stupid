package lt.markmerkk.durak

import lt.markmerkk.Mocks
import lt.markmerkk.durak.actions.ActionFinishRound
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleAttackingActionsFilter
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PossibleAttackingActionsFilterSpek : Spek({
    val possibleAttackingActionsFilter = PossibleAttackingActionsFilter()

    given("no cards at the table") {
        on("no rules bounding attacking player") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.SPADE, CardRank.ACE),
                            Card(CardSuite.SPADE, CardRank.KING),
                            Card(CardSuite.SPADE, CardRank.QUEEN),
                            Card(CardSuite.SPADE, CardRank.JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(),
                    defensivePlayerCardSizeInHand = 6
            )

            it("should be able to throw in any card") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(thrownCard = Card(CardSuite.SPADE, CardRank.ACE)),
                        ActionThrowInCard(thrownCard = Card(CardSuite.SPADE, CardRank.KING)),
                        ActionThrowInCard(thrownCard = Card(CardSuite.SPADE, CardRank.QUEEN)),
                        ActionThrowInCard(thrownCard = Card(CardSuite.SPADE, CardRank.JACK))
                )
            }
        }

    }

    given("there is one undefended card on the table") {
        on("one card of the same rank in player hand") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("should be able to throw in same rank card") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(thrownCard = Card(CardSuite.HEART, CardRank.JACK))
                )
            }
        }

        on("more than one card with same rank in player hand") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.DIAMOND, CardRank.JACK),
                            Card(CardSuite.CLUB, CardRank.JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            // Assert
            it("should be able to throw in same rank card") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(thrownCard = Card(CardSuite.HEART, CardRank.JACK)),
                        ActionThrowInCard(thrownCard = Card(CardSuite.DIAMOND, CardRank.JACK)),
                        ActionThrowInCard(thrownCard = Card(CardSuite.CLUB, CardRank.JACK))
                )
            }
        }

        on("no card of the same rank in the player hand") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("should be able to throw in same rank card") {
                assertThat(resultActions).isEmpty()
            }
        }
    }

    given("there is one defended pair on the table") {
        on("player has one more card to add") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("should be able to throw in same rank card") {
                assertThat(resultActions).contains(
                        ActionThrowInCard(thrownCard = Card(CardSuite.HEART, CardRank.JACK)),
                        ActionThrowInCard(thrownCard = Card(CardSuite.HEART, CardRank.KING))
                )
            }
        }
    }

    given("there is 6 pairs on the table") {
        on("all pairs are defended") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("no more cards can be thrown in") {
                assertThat(resultActions).doesNotHaveAnyElementsOfTypes(ActionThrowInCard::class.java)
            }
        }

        on("not all pairs are defended") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            // Assert
            it("no more cards can be thrown in") {
                assertThat(resultActions).isEmpty()
            }
        }
    }

    given("defensive player does not have enough cards for adding more") {
        on("defensive player has too little cards to defend") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.CLUB, CardRank.KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 1
            )

            it("no more cards can be thrown in, event is it should be the case") {
                assertThat(resultActions).isEmpty()
            }
        }

        on("defensive player has just the right amount of cards for defense") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 3
            )

            it("no more cards can be thrown in") {
                assertThat(resultActions).isEmpty()
            }
        }

        on("defensive player has more than enough cards to defend") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.KING),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 3
            )

            it("only one card may be thrown in") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(Card(CardSuite.HEART, CardRank.JACK)),
                        ActionThrowInCard(Card(CardSuite.HEART, CardRank.KING))
                )
            }
        }
    }

    given("figure when to show action finish round") {
        on("no cards on the table") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(emptyList()),
                    defensivePlayerCardSizeInHand = 6
            )

            it("cannot finish round") {
                assertThat(resultActions).doesNotContain(ActionFinishRound())
            }
        }

        on("not finished action") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("cannot finish round") {
                assertThat(resultActions).doesNotContain(ActionFinishRound())
            }
        }

        on("finished defending") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                            defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("should be able to finish round") {
                assertThat(resultActions).contains(ActionFinishRound())
            }
        }
    }

})

