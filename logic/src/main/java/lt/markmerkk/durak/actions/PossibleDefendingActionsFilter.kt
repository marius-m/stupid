package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.PlayingTable

/**
 * Defines possible actions for defending player
 */
class PossibleDefendingActionsFilter {

    /**
     * Filters out possible action to be taken for defensive player
     */
    fun filterActions(
            defendingPlayer: Player,
            defendingPlayerCardsInHand: List<Card>,
            playingTable: PlayingTable
    ): List<ActionGame> {
        if (playingTable.cards.isEmpty()) {
            return emptyList()
        }
        val firstUndefendedCardOnTable = playingTable.firstUndefendedCardOnTable()
        return if (firstUndefendedCardOnTable != null) {
            filterDefendableCardsAgainst(
                    firstUndefendedCardOnTable,
                    defendingPlayerCardsInHand
            ).map { ActionThrowInCard(defendingPlayer, it) }
        } else {
            emptyList()
        }
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