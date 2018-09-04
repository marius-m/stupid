package lt.markmerkk

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CliCardDrawerCardRankDisplayTest {

    private val drawer = CliCardDrawer()

    @Test
    fun upper_oneSymbol() {
        // Assemble
        // Act
        val resultRank = drawer.cardRankDisplayUpper(Card(SPADE, NINE))

        // Assert
        assertThat(resultRank).isEqualTo("9 ")
    }

    @Test
    fun upper_twoSymbols() {
        // Assemble
        // Act
        val resultRank = drawer.cardRankDisplayUpper(Card(SPADE, TEN))

        // Assert
        assertThat(resultRank).isEqualTo("10")
    }

    @Test
    fun lower_oneSymbol() {
        // Assemble
        // Act
        val resultRank = drawer.cardRankDisplayLower(Card(SPADE, NINE))

        // Assert
        assertThat(resultRank).isEqualTo("_9")
    }

    @Test
    fun lower_twoSymbols() {
        // Assemble
        // Act
        val resultRank = drawer.cardRankDisplayLower(Card(SPADE, TEN))

        // Assert
        assertThat(resultRank).isEqualTo("10")
    }

}