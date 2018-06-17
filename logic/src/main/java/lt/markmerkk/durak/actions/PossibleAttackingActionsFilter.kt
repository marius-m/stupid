package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Player
import lt.markmerkk.durak.PlayingTable
import lt.markmerkk.durak.filterSameRank

class PossibleAttackingActionsFilter: PossibleGameActionsFilter<ActionThrowInCard> {
    override fun filterActions(
            player: Player,
            playingTable: PlayingTable
    ): List<ActionThrowInCard> {
        if (playingTable.cards.isEmpty()) {
            return player.cardsInHand.map { ActionThrowInCard(it) }
        }
        return playingTable
                .filterRanksOnTable()
                .flatMap { player.cardsInHand.filterSameRank(it) }
                .map { ActionThrowInCard(it) }
    }
}