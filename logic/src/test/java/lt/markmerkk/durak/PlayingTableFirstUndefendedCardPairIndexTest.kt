package lt.markmerkk.durak

import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PlayingTableFirstUndefendedCardPairIndexTest {

    private val playingTable = PlayingTable(emptyList())

    @Test
    fun empty() {
        // Assemble
        // Act
        val resultPairIndex = playingTable.firstUndefendedCardPairIndex()

        // Assert
        assertThat(resultPairIndex).isEqualTo(-1)
    }

    @Test
    fun firstUndefended() {
        // Assemble
        playingTable.cards = listOf(PlayingCardPair(
                attackingCard = Card(SPADE, TEN),
                defendingCard = null
        ))

        // Act
        val resultPairIndex = playingTable.firstUndefendedCardPairIndex()

        // Assert
        assertThat(resultPairIndex).isEqualTo(0)
    }

    @Test
    fun multipleUndefended() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = null
                ),
                PlayingCardPair(
                        attackingCard = Card(DIAMOND, TEN),
                        defendingCard = null
                )
        )

        // Act
        val resultPairIndex = playingTable.firstUndefendedCardPairIndex()

        // Assert
        assertThat(resultPairIndex).isEqualTo(0)
    }

    @Test
    fun secondUndefended() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = Card(SPADE, JACK)
                ),
                PlayingCardPair(
                        attackingCard = Card(DIAMOND, TEN),
                        defendingCard = null
                )
        )

        // Act
        val resultPairIndex = playingTable.firstUndefendedCardPairIndex()

        // Assert
        assertThat(resultPairIndex).isEqualTo(1)
    }

}