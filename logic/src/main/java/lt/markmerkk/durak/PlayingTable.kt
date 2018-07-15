package lt.markmerkk.durak

/**
 * Defines playing table, where attacking player tries to get rid of his cards
 */
class PlayingTable(
        var cards: List<PlayingCardPair>
) {

    /**
     * Place attacking [Card] on the [PlayingTable]
     * Attacking [Card] must be of the same [CardRank] as any card on the [PlayingTable]
     * Attacking card pairs cannot exceed [Consts.MAX_PAIRS_ON_TABLE]
     */
    @Throws(IllegalArgumentException::class)
    fun attack(card: Card) {
        if (cards.size >= Consts.MAX_PAIRS_ON_TABLE) {
            throw IllegalArgumentException("Table already has max card pairs (${Consts.MAX_PAIRS_ON_TABLE})")
        }
        val ranksOnTable = filterRanksOnTable()
        val cardRankIsOnTable = ranksOnTable.contains(card.rank)
        if (cards.isNotEmpty() && !cardRankIsOnTable) {
            throw IllegalArgumentException("No such rank on the table (throwing in $card, $ranksOnTable on table )")
        }
        cards = cards.plus(
                PlayingCardPair(
                        attackingCard = card,
                        defendingCard = null
                )
        )
    }

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

    /**
     * Filters out all undefended cards on the table
     */
    fun undefendedCardsOnTable(): List<Card> = cards
            .filter { it.defendingCard == null }
            .map { it.attackingCard }

    fun firstUndefendedCardOnTable(): Card? {
        return cards
                .find { it.defendingCard == null }
                ?.attackingCard
    }

    override fun toString(): String {
        val cardsOnTable = cards
                .map { "${it.attackingCard}/${it.defendingCard}" }
                .joinToString(",")
        return "PlayingTable:($cardsOnTable)"
    }
}

data class PlayingCardPair(
        val attackingCard: Card,
        val defendingCard: Card?
)