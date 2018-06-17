package lt.markmerkk.durak

import com.sun.jmx.remote.internal.ArrayQueue
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.*

object PlayerRefillSpek: Spek({
    given("deck has enough cards") {
        val refillingDeck = RefillingDeck(
                cards = ArrayDeque<Card>(
                        listOf<Card>(
                                Card(CardSuite.SPADE, CardRank.ACE),
                                Card(CardSuite.SPADE, CardRank.KING),
                                Card(CardSuite.SPADE, CardRank.QUEEN),
                                Card(CardSuite.SPADE, CardRank.JACK),
                                Card(CardSuite.SPADE, CardRank.TEN),
                                Card(CardSuite.SPADE, CardRank.NINE),
                                Card(CardSuite.SPADE, CardRank.EIGHT)
                        )
                )
        )
        on("empty hand") {
            val player = Player(
                    name = "valid_player",
                    cardsInHand = emptyList()
            )
            player.refill(refillingDeck = refillingDeck)
            it("player should have refilled hand") {
                assertThat(refillingDeck.cards.size).isEqualTo(7)
                assertThat(player.cardsInHand.size).isEqualTo(6)
            }
        }
    }
})

