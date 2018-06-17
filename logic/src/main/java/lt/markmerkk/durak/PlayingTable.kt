package lt.markmerkk.durak

/**
 * Defines playing table, where attacking player tries to get rid of his cards
 */
data class PlayingTable(
        var cards: List<PlayingCardPair>
) {
    /**
     * Filters out all the ranks on the table
     */
    fun filterRanksOnTable(): Set<CardRank> {
        val attackingCardRanks = cards.map { it.attackingCard.rank }.toSet()
        val defendingCardRanks = cards
                .filter { it.defendingCard != null }
                .map { it.defendingCard!!.rank }
                .toSet()
        return attackingCardRanks.plus(defendingCardRanks)
    }
}

data class PlayingCardPair(
        val attackingCard: Card,
        val defendingCard: Card?
)