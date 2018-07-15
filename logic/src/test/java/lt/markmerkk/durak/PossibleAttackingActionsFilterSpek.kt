package lt.markmerkk.durak

import lt.markmerkk.Mocks
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import lt.markmerkk.durak.actions.ActionFinishRound
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleAttackingActionsFilter
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PossibleAttackingActionsFilterSpek : Spek({
    val attackingPlayer = Player(name = "Marius")
    val possibleAttackingActionsFilter = PossibleAttackingActionsFilter()

    given("no cards at the table") {
        on("no rules bounding attacking player") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, QUEEN),
                            Card(SPADE, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(),
                    defensivePlayerCardSizeInHand = 6
            )

            it("should be able to throw in any card") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(SPADE, ACE)),
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(SPADE, KING)),
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(SPADE, QUEEN)),
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(SPADE, JACK))
                )
            }
        }

    }

    given("there is one undefended card on the table") {
        on("one card of the same rank in player hand") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("should be able to throw in same rank card") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(HEART, JACK))
                )
            }
        }

        on("more than one card with same rank in player hand") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK),
                            Card(DIAMOND, JACK),
                            Card(CLUB, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            // Assert
            it("should be able to throw in same rank card") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(HEART, JACK)),
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(DIAMOND, JACK)),
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(CLUB, JACK))
                )
            }
        }

        on("no card of the same rank in the player hand") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
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
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("should be able to throw in same rank card") {
                assertThat(resultActions).contains(
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(HEART, JACK)),
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(HEART, KING))
                )
            }
        }
    }

    given("there is 6 pairs on the table") {
        on("all pairs are defended") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK),
                            Card(HEART, TEN),
                            Card(HEART, EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
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
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK),
                            Card(HEART, TEN),
                            Card(HEART, EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
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
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK),
                            Card(HEART, TEN),
                            Card(HEART, EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(CLUB, KING)
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
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
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK),
                            Card(HEART, TEN),
                            Card(HEART, EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
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
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK),
                            Card(HEART, TEN),
                            Card(HEART, EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, KING),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 3
            )

            it("only one card may be thrown in") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(HEART, JACK)),
                        ActionThrowInCard(actionIssuer = attackingPlayer, thrownCard = Card(HEART, KING))
                )
            }
        }
    }

    given("figure when to show action finish round") {
        on("no cards on the table") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK),
                            Card(HEART, TEN),
                            Card(HEART, EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(emptyList()),
                    defensivePlayerCardSizeInHand = 6
            )

            it("cannot finish round") {
                assertThat(resultActions).doesNotContain(ActionFinishRound(actionIssuer = attackingPlayer))
            }
        }

        on("not finished action") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK),
                            Card(HEART, TEN),
                            Card(HEART, EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = null
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("cannot finish round") {
                assertThat(resultActions).doesNotContain(ActionFinishRound(actionIssuer = attackingPlayer))
            }
        }

        on("finished defending") {
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    attackingPlayer = attackingPlayer,
                    attackingPlayerCardsInHand = Mocks.createCards(
                            Card(HEART, ACE),
                            Card(HEART, KING),
                            Card(HEART, QUEEN),
                            Card(HEART, JACK),
                            Card(HEART, TEN),
                            Card(HEART, EIGHT)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, JACK),
                                            defendingCard = Card(SPADE, KING)
                                    )
                            )
                    ),
                    defensivePlayerCardSizeInHand = 6
            )

            it("should be able to finish round") {
                assertThat(resultActions).contains(ActionFinishRound(actionIssuer = attackingPlayer))
            }
        }
    }

})

