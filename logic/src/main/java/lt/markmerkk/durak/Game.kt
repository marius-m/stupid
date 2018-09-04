package lt.markmerkk.durak

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
            logger.info("${actionThrowInCard.actionIssuer.name} cannot throw in ${actionThrowInCard.thrownCard}!\n")
            logger.info(printAvailablePlayerActions(actionThrowInCard.actionIssuer))
        } else {
            val actionPlayer = actionThrowInCard.actionIssuer
            playingTable.attack(actionThrowInCard.thrownCard)
            actionPlayer.removeCard(actionThrowInCard.thrownCard)
            logger.info("${actionPlayer.name} throws ${actionThrowInCard.thrownCard}\n")
        }
    }

    /**
     * Forms available actions for a player
     */
    fun printAvailablePlayerActions(
            player: Player
    ): String {
        val availableActions = when {
            turnsManager.isAttacking(player) -> attackingActionsFilter.filterActions(
                    attackingPlayer = player,
                    attackingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable,
                    defensivePlayerCardSizeInHand = turnsManager.defendingPlayer.cardsInHandSize()
            )
            turnsManager.isDefending(player) -> defendingActionsFilter.filterActions(
                    defendingPlayer = player,
                    defendingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable
            )
            else -> emptyList()
        }
        if (availableActions.isEmpty()) {
            return "${player.name} cannot perform any actions at this moment!\n"
        }
        val availableActionDescriptions = availableActions
                .map { it.actionUseDescription }
                .joinToString(separator = ",\n\t", prefix = "\t")
        return "${player.name} can do these actions:\n$availableActionDescriptions\n"
    }

    //endregion

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }

}