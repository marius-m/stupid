package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.PlayingTable

/**
 * Defines possible actions for defending player
 */
class PossibleDefendingActionsFilter {

    /**
     * Filters out possible action to be taken for defensive player
     */
    fun filterActions(
            defendingPlayerCardsInHand: List<Card>,
            playingTable: PlayingTable
    ): List<ActionGame> {
        if (playingTable.cards.isEmpty()) {
            return emptyList()
        }
        return defendingPlayerCardsInHand
                .map { ActionThrowInCard(it) }
    }

    /**
     * Filters out only possible higher cards pitted agains [attackingCard]
     */
    fun filterDefendableCardsAgainst(
            attackingCard: Card,
            defendableCards: List<Card>
    ): List<Card> {
        val regularDefendableCards = defendableCards
                .filter { !it.isTrump }
                .filter { attackingCard.suite == it.suite }
                .filter { attackingCard < it }
        val trumpDefendableCards = defendableCards
                .filter { it.isTrump }
                .filter { attackingCard < it }
        return regularDefendableCards.plus(trumpDefendableCards)
    }

}