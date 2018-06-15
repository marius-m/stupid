package lt.markmerkk.durak

/**
 * Defines playing table, where attacking player tries to get rid of his cards
 */
data class PlayingTable(
        var cards: List<Pair<Card, Card?>>
)