package lt.markmerkk.durak

import com.nhaarman.mockito_kotlin.*
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleAttackingActionsFilter
import lt.markmerkk.durak.actions.PossibleDefendingActionsFilter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

// todo: Incomplete suite
class GameThrowCardTest {

    @Mock lateinit var turnsManager: TurnsManager
    @Mock lateinit var refillingDeck: RefillingDeck
    @Mock lateinit var playingTable: PlayingTable
    @Mock lateinit var attackingActionsFilter: PossibleAttackingActionsFilter
    @Mock lateinit var defendingActionsFilter: PossibleDefendingActionsFilter

    lateinit var game: Game

    private val playerAttacking = spy(Player(
            name = "Marius",
            cardsInHand = listOf(
                    Card(HEART, ACE),
                    Card(HEART, KING),
                    Card(HEART, QUEEN)
            )
    ))
    private val playerDefending = spy(Player(
            name = "Enrika",
            cardsInHand = listOf(
                    Card(SPADE, ACE),
                    Card(SPADE, KING),
                    Card(SPADE, QUEEN)
            )
    ))
    private val players = listOf(playerAttacking, playerDefending)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        game = Game(
                cards = Card.generateDeck(),
                players = players,
                turnsManager = turnsManager,
                refillingDeck = refillingDeck,
                playingTable = playingTable,
                attackingActionsFilter = attackingActionsFilter,
                defendingActionsFilter = defendingActionsFilter
        )
        doReturn(playerAttacking).whenever(turnsManager).attackingPlayer
        doReturn(playerDefending).whenever(turnsManager).defendingPlayer
    }

    @Test
    fun attack_validAction() {
        // Assemble
        val card = Card(HEART, KING)
        val action = ActionThrowInCard(
                actionIssuer = playerAttacking,
                thrownCard = card
        )
        doReturn(listOf(action)).whenever(attackingActionsFilter).filterActions(any(), any(), any(), any())

        // Act
        game.throwCard(action)

        // Assert
        verify(playingTable).attack(card)
        verify(playerAttacking).removeCard(card)
    }

    @Test
    fun attack_invalid_action() {
        // Assemble
        val card = Card(HEART, KING)
        val action = ActionThrowInCard(
                actionIssuer = playerAttacking,
                thrownCard = card
        )
        doReturn(emptyList<ActionGame>()).whenever(attackingActionsFilter).filterActions(any(), any(), any(), any()) // Action cannot be performed

        // Act
        game.throwCard(action)

        // Assert
        verify(playingTable, never()).attack(card)
        verify(playerAttacking, never()).removeCard(card)
    }

    @Test
    fun defend_validAction() {
        // Assemble
        val card = Card(SPADE, KING)
        val action = ActionThrowInCard(
                actionIssuer = playerDefending,
                thrownCard = card
        )
        doReturn(listOf(action)).whenever(defendingActionsFilter).filterActions(any(), any(), any())

        // Act
        game.throwCard(action)

        // Assert
        verify(playingTable).attack(card)
        verify(playerAttacking).removeCard(card)
    }

}