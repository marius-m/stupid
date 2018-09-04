package lt.markmerkk.durak

import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleAttackingActionsFilter
import lt.markmerkk.durak.actions.PossibleDefendingActionsFilter
import org.slf4j.LoggerFactory
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
        if (!availableAttackerActions.contains(actionThrowInCard)) {
            val availableActionDescriptions = availableAttackerActions
                    .map { it.actionUseDescription }
                    .joinToString(separator = ",\n\t\t", prefix = "\t\t")
            logger.info("${actionThrowInCard.actionIssuer.name} cannot throw in ${actionThrowInCard.thrownCard}!\n")
            if (availableAttackerActions.isEmpty()) {
                logger.info("${actionThrowInCard.actionIssuer.name} cannot take any actions at this moment!")
            } else {
                logger.info("Available actions for ${actionThrowInCard.actionIssuer.name} are:\n\t[\n$availableActionDescriptions\n\t]\n")
            }
        } else {
            val actionPlayer = actionThrowInCard.actionIssuer
            playingTable.attack(actionThrowInCard.thrownCard)
            actionPlayer.removeCard(actionThrowInCard.thrownCard)
            logger.info("${actionPlayer.name} throws ${actionThrowInCard.thrownCard}\n")
        }
    }

    //endregion

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }

}