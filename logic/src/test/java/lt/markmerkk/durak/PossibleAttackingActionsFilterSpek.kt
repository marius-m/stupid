package lt.markmerkk.durak

import lt.markmerkk.Mocks
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleAttackingActionsFilter
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PossibleAttackingActionsFilterSpek : Spek({
    lateinit var player: Player
    lateinit var playingTable: PlayingTable
    lateinit var possibleAttackingActionsFilter: PossibleAttackingActionsFilter

    given("no cards at the table") {
        on("no rules bounding attacking player") {
            // Assemble
            player = Mocks.createPlayer(
                    cardsInHand = listOf(
                            Card(CardSuite.SPADE, CardRank.ACE),
                            Card(CardSuite.SPADE, CardRank.KING),
                            Card(CardSuite.SPADE, CardRank.QUEEN),
                            Card(CardSuite.SPADE, CardRank.JACK)
                    )
            )
            playingTable = Mocks.createPlayingTable()
            possibleAttackingActionsFilter = PossibleAttackingActionsFilter()

            // Act
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    player,
                    playingTable
            )

            // Assert
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
            // Assemble
            player = Mocks.createPlayer(
                    cardsInHand = listOf(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK)
                    )
            )
            playingTable = Mocks.createPlayingTable(
                    listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                    defendingCard = null
                            )
                    )
            )
            possibleAttackingActionsFilter = PossibleAttackingActionsFilter()

            // Act
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    player,
                    playingTable
            )

            // Assert
            it("should be able to throw in same rank card") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(thrownCard = Card(CardSuite.HEART, CardRank.JACK))
                )
            }
        }

        on("more than one card with same rank in player hand") {
            // Assemble
            player = Mocks.createPlayer(
                    cardsInHand = listOf(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK),
                            Card(CardSuite.DIAMOND, CardRank.JACK),
                            Card(CardSuite.CLUB, CardRank.JACK)
                    )
            )
            playingTable = Mocks.createPlayingTable(
                    listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                    defendingCard = null
                            )
                    )
            )
            possibleAttackingActionsFilter = PossibleAttackingActionsFilter()

            // Act
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    player,
                    playingTable
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
            // Assemble
            player = Mocks.createPlayer(
                    cardsInHand = listOf(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN)
                    )
            )
            playingTable = Mocks.createPlayingTable(
                    listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                    defendingCard = null
                            )
                    )
            )
            possibleAttackingActionsFilter = PossibleAttackingActionsFilter()

            // Act
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    player,
                    playingTable
            )

            // Assert
            it("should be able to throw in same rank card") {
                assertThat(resultActions).isEmpty()
            }
        }
    }

    given("there is one defended pair on the table") {
        on("player has one more card to add") {
            // Assemble
            player = Mocks.createPlayer(
                    cardsInHand = listOf(
                            Card(CardSuite.HEART, CardRank.ACE),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.JACK)
                    )
            )
            playingTable = Mocks.createPlayingTable(
                    listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                    defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                            )
                    )
            )
            possibleAttackingActionsFilter = PossibleAttackingActionsFilter()

            // Act
            val resultActions = possibleAttackingActionsFilter.filterActions(
                    player,
                    playingTable
            )

            // Assert
            it("should be able to throw in same rank card") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(thrownCard = Card(CardSuite.HEART, CardRank.JACK)),
                        ActionThrowInCard(thrownCard = Card(CardSuite.HEART, CardRank.KING))
                )
            }
        }
    }

})

