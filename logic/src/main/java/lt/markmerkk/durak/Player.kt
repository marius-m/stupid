package lt.markmerkk.durak

/**
 * Virtual "holder" of cards
 */
data class Player(
        val name: String,
        var cardsInHand: List<Card> = emptyList()
) {
    fun refill(refillingDeck: RefillingDeck) {
        val refilledCards = cardsInHand.toMutableList()
        val cardsToRefill = Consts.MAX_REFILL_HAND - cardsInHand.size
        (1..cardsToRefill).forEach {
            if (refillingDeck.cards.isNotEmpty()) {
                refilledCards.add(refillingDeck.cards.poll())
            }
        }
        cardsInHand = refilledCards.toList()
    }
}