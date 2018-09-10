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

    fun resetGame() {
        players.forEach { it.reset() }
    }

    fun refillPlayerCards() {
        players.forEach { it.refill(refillingDeck) }
    }

    fun throwCard(actionThrowInCard: ActionThrowInCard) {
        val player = actionThrowInCard.actionIssuer
        if (turnsManager.isAttacking(player)) {
            val availableActions = attackingActionsFilter.filterActions(
                    attackingPlayer = player,
                    attackingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable,
                    defensivePlayerCardSizeInHand = turnsManager.defendingPlayer.cardsInHandSize()
            )
            if (availableActions.isNotEmpty()) {
                try {
                    playingTable.attack(actionThrowInCard.thrownCard)
                    player.removeCard(actionThrowInCard.thrownCard)
                    logger.info("${player.name} throws ${actionThrowInCard.thrownCard}\n")
                } catch (e: IllegalArgumentException) {
                    logger.warn("Cannot perform this operation when trying to attack", e)
                }
            } else {
                logger.info("${player.name} cannot throw in ${actionThrowInCard.thrownCard}!\n")
                logger.info(printAvailablePlayerActions(player))
            }
        } else if (turnsManager.isDefending(player)) {
            val availableActions = defendingActionsFilter.filterActions(
                    defendingPlayer = player,
                    defendingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable
            )
            if (availableActions.isNotEmpty()) {
                try {
                    playingTable.defend(actionThrowInCard.thrownCard)
                    player.removeCard(actionThrowInCard.thrownCard)
                    logger.info("${player.name} throws ${actionThrowInCard.thrownCard}\n")
                } catch (e: IllegalArgumentException) {
                    logger.warn("Illegal action performed trying to defend", e)
                }
            } else {
                logger.info("${player.name} cannot throw in ${actionThrowInCard.thrownCard}!\n")
                logger.info(printAvailablePlayerActions(player))
            }
        } else {
            TODO("Unsupported operation")
        }
    }

    //region Convenience

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