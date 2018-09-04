package lt.markmerkk.durak

import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleAttackingActionsFilter
import lt.markmerkk.durak.actions.PossibleDefendingActionsFilter
import java.util.*

class Game(
        cards: List<Card>,
        private val players: List<Player>,
        private val turnsManager: TurnsManager,
        private val refillingDeck: RefillingDeck = RefillingDeck(cards = ArrayDeque<Card>(cards.toMutableList().shuffled())),
        private val playingTable: PlayingTable = PlayingTable(cards = emptyList()),
        private val attackingActionsFilter: PossibleAttackingActionsFilter = PossibleAttackingActionsFilter(playingTable),
        private val defendingActionsFilter: PossibleDefendingActionsFilter = PossibleDefendingActionsFilter()
) {

    var isGameOver = false

    //region Game actions

    fun resetGame() {
        players.forEach { it.reset() }
    }

    fun refillPlayerCards() {
        players.forEach { it.refill(refillingDeck) }
    }

    // todo: Missing tests
    fun throwCard(actionThrowInCard: ActionThrowInCard) {
        val availableAttackerActions = attackingActionsFilter.availableActions(
                actionGame = actionThrowInCard,
                defensivePlayerCardSizeInHand = turnsManager.defendingPlayer.cardsInHandSize()
        )
        try {
            if (!availableAttackerActions.contains(actionThrowInCard)) {
                throw IllegalArgumentException("Action is not available!")
            }
            val actionPlayer = actionThrowInCard.actionIssuer
            playingTable.attack(actionThrowInCard.thrownCard)
            actionPlayer.removeCard(actionThrowInCard.thrownCard)
            println("[INFO] ${actionPlayer.name} throws ${actionThrowInCard.thrownCard}")
        } catch (e: IllegalArgumentException) {
            println("[ERROR] ${e.message}")
            println("[INFO] Available actions for ${actionThrowInCard.actionIssuer} are: $availableAttackerActions")
        }
    }

    //endregion

    fun inputTurn(action: ActionGame) {
        println("Entered action $action")
    }
}