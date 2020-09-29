package lt.markmerkk.durak

import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class PlayingTableDefendTest {

    lateinit var playingTable: PlayingTable

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playingTable = PlayingTable(emptyList())
    }

    @Test
    fun valid() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = null
                )
        )

        // Act
        playingTable.defend(Card(SPADE, JACK))

        // Assert
        assertThat(playingTable.cards.first().defendingCard).isEqualTo(Card(SPADE, JACK))
    }

    @Test(expected = IllegalArgumentException::class)
    fun lowerCard() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = null
                )
        )

        // Act
        playingTable.defend(Card(SPADE, NINE))

        // Assert
        fail("Lower card")
    }

    @Test(expected = IllegalArgumentException::class)
    fun differentSuite() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = null
                )
        )

        // Act
        playingTable.defend(Card(HEART, JACK))

        // Assert
        fail("Incorrect suite")
    }

    @Test
    fun differentSuite_trumpCard() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = null
                )
        )

        // Act
        playingTable.defend(Card(HEART, JACK, isTrump = true))

        // Assert
        assertThat(playingTable.cards.first().defendingCard).isEqualTo(Card(HEART, JACK, isTrump = true))
    }

    @Test
    fun bothTrumps_higher() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN, isTrump = true),
                        defendingCard = null
                )
        )

        // Act
        playingTable.defend(Card(SPADE, JACK, isTrump = true))

        // Assert
        assertThat(playingTable.cards.first().defendingCard).isEqualTo(Card(SPADE, JACK, isTrump = true))
    }

    @Test(expected = IllegalArgumentException::class)
    fun bothTrumps_lower() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN, isTrump = true),
                        defendingCard = null
                )
        )

        // Act
        playingTable.defend(Card(SPADE, NINE, isTrump = true))

        // Assert
        fail("Card too low")
    }

    @Test(expected = IllegalArgumentException::class)
    fun nothingToDefendAgainst() {
        // Assemble
        playingTable.cards = emptyList()

        // Act
        playingTable.defend(Card(SPADE, JACK))

        // Assert
        fail("Nothing to defend against")
    }

}