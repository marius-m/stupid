package lt.markmerkk.durak

data class Card(
        val suite: CardSuite,
        val rank: CardRank
) {
    companion object {
        fun generateDeck(): List<Card> {
            val cards = mutableListOf<Card>()
            for (suite in CardSuite.values()) {
                for (rank in CardRank.values()) {
                    cards.add(Card(suite, rank))
                }
            }
            return cards
        }
    }
}

enum class CardSuite {
    SPADE,
    HEART,
    DIAMOND,
    CLUB
}

enum class CardRank {
    ACE,
    KING,
    QUEEN,
    JACK,
    TEN,
    NINE,
    EIGHT,
    SEVEN,
    SIX,
    FIVE,
    FOUR,
    THREE,
    TWO
}