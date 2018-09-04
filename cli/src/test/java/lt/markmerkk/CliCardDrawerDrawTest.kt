package lt.markmerkk

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.CardRank
import lt.markmerkk.durak.CardRank.ACE
import lt.markmerkk.durak.CardSuite.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.MockitoAnnotations

class CliCardDrawerDrawTest {

    lateinit var drawer: CliCardDrawer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        drawer = CliCardDrawer()
    }

    @Test
    fun valid_club() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(CLUB, ACE))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|A  _   |\n" +
                        "|  ( )  |\n" +
                        "| (_'_) |\n" +
                        "|   |   |\n" +
                        "|______A|\n"
        )
    }

    @Test
    fun valid_club_twoSymbols() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(CLUB, CardRank.TEN))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|10 _   |\n" +
                        "|  ( )  |\n" +
                        "| (_'_) |\n" +
                        "|   |   |\n" +
                        "|_____10|\n"
        )
    }

    @Test
    fun allClubs() {
        // Assemble
        val allRanks = CardRank
                .values()
                .map { cardRank ->  Card(CLUB, cardRank) }

        // Act
        val resultDrawing = drawer.drawCards(allRanks)

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______ \n" +
                        "|A  _   ||K  _   ||Q  _   ||J  _   ||10 _   ||9  _   ||8  _   ||7  _   ||6  _   ||5  _   ||4  _   ||3  _   ||2  _   |\n" +
                        "|  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  |\n" +
                        "| (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) |\n" +
                        "|   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   |\n" +
                        "|______A||______K||______Q||______J||_____10||______9||______8||______7||______6||______5||______4||______3||______2|\n"
        )
    }

    @Test
    fun valid_heart() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(HEART, ACE))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|A _ _  |\n" +
                        "| ( v ) |\n" +
                        "|  \\ /  |\n" +
                        "|   .   |\n" +
                        "|______A|\n"
        )
    }

    @Test
    fun allHearts() {
        // Assemble
        val allRanks = CardRank
                .values()
                .map { cardRank ->  Card(HEART, cardRank) }

        // Act
        val resultDrawing = drawer.drawCards(allRanks)

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______ \n" +
                        "|A _ _  ||K _ _  ||Q _ _  ||J _ _  ||10_ _  ||9 _ _  ||8 _ _  ||7 _ _  ||6 _ _  ||5 _ _  ||4 _ _  ||3 _ _  ||2 _ _  |\n" +
                        "| ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) |\n" +
                        "|  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  |\n" +
                        "|   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   |\n" +
                        "|______A||______K||______Q||______J||_____10||______9||______8||______7||______6||______5||______4||______3||______2|\n"
        )
    }

    @Test
    fun valid_diamond() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(DIAMOND, ACE))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|A  ^   |\n" +
                        "|  / \\  |\n" +
                        "|  \\ /  |\n" +
                        "|   .   |\n" +
                        "|______A|\n"
        )
    }

    @Test
    fun allDiamonds() {
        // Assemble
        val allRanks = CardRank
                .values()
                .map { cardRank ->  Card(DIAMOND, cardRank) }

        // Act
        val resultDrawing = drawer.drawCards(allRanks)

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______ \n" +
                        "|A  ^   ||K  ^   ||Q  ^   ||J  ^   ||10 ^   ||9  ^   ||8  ^   ||7  ^   ||6  ^   ||5  ^   ||4  ^   ||3  ^   ||2  ^   |\n" +
                        "|  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  |\n" +
                        "|  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  |\n" +
                        "|   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   |\n" +
                        "|______A||______K||______Q||______J||_____10||______9||______8||______7||______6||______5||______4||______3||______2|\n"
        )
    }

    @Test
    fun valid_spade() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(SPADE, ACE))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|A  .   |\n" +
                        "|  /.\\  |\n" +
                        "| (_._) |\n" +
                        "|   |   |\n" +
                        "|______A|\n"
        )
    }

    @Test
    fun allSpades() {
        // Assemble
        val allRanks = CardRank
                .values()
                .map { cardRank ->  Card(SPADE, cardRank) }

        // Act
        val resultDrawing = drawer.drawCards(allRanks)

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______ \n" +
                        "|A  .   ||K  .   ||Q  .   ||J  .   ||10 .   ||9  .   ||8  .   ||7  .   ||6  .   ||5  .   ||4  .   ||3  .   ||2  .   |\n" +
                        "|  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  |\n" +
                        "| (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) |\n" +
                        "|   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   |\n" +
                        "|______A||______K||______Q||______J||_____10||______9||______8||______7||______6||______5||______4||______3||______2|\n"
        )
    }

}