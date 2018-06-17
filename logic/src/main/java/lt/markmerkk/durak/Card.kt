package lt.markmerkk.durak

data class Card(
        val suite: CardSuite,
        val rank: CardRank,
        val isTrump: Boolean = false
): Comparable<Card> {

    override fun compareTo(other: Card): Int {
        if (weight() > other.weight()) {
            return 1
        }
        if (weight() == other.weight()) {
            return 0
        }
        return -1
    }

    fun weight(): Int = if (isTrump) rank.weight + 100 else rank.weight

    override fun toString(): String {
        return "Card(${suite.out}${rank.out})"
    }

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

fun List<Card>.filterSameRank(rank: CardRank): List<Card> = filter { it.rank == rank }

enum class CardSuite(
        val out: String
) {
    SPADE("S"),
    HEART("H"),
    DIAMOND("D"),
    CLUB("C"),
    ;
}

enum class CardRank(
        val out: String,
        val weight: Int
) {
    ACE("A", 14),
    KING("K", 13),
    QUEEN("Q", 12),
    JACK("J", 11),
    TEN("10", 10),
    NINE("9", 9),
    EIGHT("8", 8),
    SEVEN("7", 7),
    SIX("6", 6),
    FIVE("5", 5),
    FOUR("4", 4),
    THREE("3", 3),
    TWO("2", 2),
    ;
}