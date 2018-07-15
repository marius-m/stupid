package lt.markmerkk.durak

import com.nhaarman.mockito_kotlin.*
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleAttackingActionsFilter
import lt.markmerkk.durak.actions.PossibleDefendingActionsFilter
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object GameThrowCardSpek: Spek({
    lateinit var player1: Player
    lateinit var player2: Player
    lateinit var players: List<Player>
    lateinit var playingTable: PlayingTable
    lateinit var attackingActionsFilter: PossibleAttackingActionsFilter
    lateinit var defendingActionsFilter: PossibleDefendingActionsFilter
    lateinit var game: Game

    beforeEachTest {
        player1 = spy(Player(
                name = "attacking_player",
                cardsInHand = listOf(Card(DIAMOND, NINE))
        ))
        player2 = spy(Player(
                name = "defending_player",
                cardsInHand = listOf(Card(DIAMOND, TEN))
        ))
        players = listOf(player1, player2)
        playingTable = mock()
        attackingActionsFilter = mock()
        defendingActionsFilter = mock()
        game = Game(
                cards = emptyList(),
                players = players,
                turnsManager = TurnsManager(players),
                playingTable = playingTable,
                attackingActionsFilter = attackingActionsFilter,
                defendingActionsFilter = defendingActionsFilter
        )
    }

    given("throw actions works properly") {
        on("valid attacker action") {
            val availableAttackerActions = listOf(ActionThrowInCard(player1, Card(DIAMOND, NINE)))
            doReturn(availableAttackerActions).whenever(attackingActionsFilter).availableActions(any(), any())

            game.throwCard(ActionThrowInCard(actionIssuer = player1, thrownCard = Card(DIAMOND, NINE)))

            it("should remove player card in hand") {
                verify(player1).removeCard(Card(DIAMOND, NINE))
            }
            it("should add card to playing table") {
                verify(playingTable).attack(Card(DIAMOND, NINE))
            }
        }

        on("attacker action not available") {
            val availableAttackerActions = emptyList<ActionGame>()
            doReturn(availableAttackerActions).whenever(attackingActionsFilter).availableActions(any(), any())

            game.throwCard(ActionThrowInCard(actionIssuer = player1, thrownCard = Card(DIAMOND, NINE)))

            it("should not do any interactions") {
                verifyZeroInteractions(player1)
            }
            it("should not do any interactions") {
                verifyZeroInteractions(playingTable)
            }
        }

        on("failure putting card on playing deck") {
            val availableAttackerActions = listOf(ActionThrowInCard(player1, Card(DIAMOND, NINE)))
            doReturn(availableAttackerActions).whenever(attackingActionsFilter).availableActions(any(), any())
            doThrow(IllegalArgumentException()).whenever(playingTable).attack(any())

            game.throwCard(ActionThrowInCard(actionIssuer = player1, thrownCard = Card(DIAMOND, NINE)))

            it("should not remove card from player") {
                verifyZeroInteractions(player1)
            }
        }
    }
})

