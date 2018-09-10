package lt.markmerkk.durak

import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

// todo: Incomplete imopl
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
        fail("Incorrect rank")
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