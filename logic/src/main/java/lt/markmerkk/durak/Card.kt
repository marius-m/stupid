package lt.markmerkk.durak

data class Card(
        val suite: CardSuite,
        val rank: CardRank
) {

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
        val out: String
) {
    ACE("A"),
    KING("K"),
    QUEEN("Q"),
    JACK("J"),
    TEN("10"),
    NINE("9"),
    EIGHT("8"),
    SEVEN("7"),
    SIX("6"),
    FIVE("5"),
    FOUR("4"),
    THREE("3"),
    TWO("2"),
    ;
}