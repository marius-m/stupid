package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Consts
import lt.markmerkk.durak.PlayingTable
import lt.markmerkk.durak.filterSameRank

/**
 * Defines possible actions for attacking player
 */
class PossibleAttackingActionsFilter {

    fun filterActions(
            attackingPlayerCardsInHand: List<Card>,
            playingTable: PlayingTable,
            defensivePlayerCardSizeInHand: Int
    ): List<ActionGame> {
        val resultCards: MutableList<ActionGame> = mutableListOf()
        val throwInCardsActions = handleThrowInCardsActions(
                attackingPlayerCardsInHand,
                playingTable,
                defensivePlayerCardSizeInHand
        )
        resultCards.addAll(throwInCardsActions)
        resultCards.addAll(handleFinishRoundActions(playingTable))
        return resultCards.toList()
    }

    private fun handleFinishRoundActions(
            playingTable: PlayingTable
    ): List<ActionGame> {
        if (playingTable.cards.isEmpty()) {
            return emptyList()
        }
        if (playingTable.undefendedCardsOnTable().isEmpty()) {
            return listOf(ActionFinishRound())
        }
        return emptyList()
    }

    private fun handleThrowInCardsActions(
            attackingPlayerCardsInHand: List<Card>,
            playingTable: PlayingTable,
            defensivePlayerCardSizeInHand: Int
    ): List<ActionGame> {
        if (playingTable.cards.isEmpty()) {
            return attackingPlayerCardsInHand.map { ActionThrowInCard(it) }
        }
        val undefendedCardsOnTable = playingTable.undefendedCardsOnTable()
        if (undefendedCardsOnTable.size >= defensivePlayerCardSizeInHand) {
            return emptyList()
        }
        if (playingTable.cards.size == Consts.MAX_PAIRS_ON_TABLE) {
            return emptyList()
        }
        return playingTable
                .filterRanksOnTable()
                .flatMap { attackingPlayerCardsInHand.filterSameRank(it) }
                .map { ActionThrowInCard(it) }
    }

}