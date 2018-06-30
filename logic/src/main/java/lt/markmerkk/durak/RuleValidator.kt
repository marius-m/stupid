package lt.markmerkk.durak

import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.durak.actions.ActionThrowInCard

/**
 * Defines possible rules for a player to trigger
 * todo: Again, terrible name, take care of it later on
 */
class RuleValidator(
        val player: Player
) {
    fun possibleActions(
            turnsManager: TurnsManager,
            playingTable: PlayingTable
    ): List<ActionGame> {
        if (turnsManager.isAttacking(player)) {
            if (playingTable.cards.isEmpty()) {
                return player.cardsInHand.map {
                    ActionThrowInCard(it)
                }
            } else {
                return player
                        .cardsInHand
                        .filterSameRank(playingTable.cards.first().attackingCard.rank)
                        .map { ActionThrowInCard(it) }
            }
        } else if (turnsManager.isDefending(player)) {
            return emptyList<ActionThrowInCard>()
        } else {
            return emptyList<ActionThrowInCard>()
        }
    }
}