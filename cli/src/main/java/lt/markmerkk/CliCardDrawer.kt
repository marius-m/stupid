package lt.markmerkk

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.CardSuite

/**
 * Responsible for drawing cards
 * Source: https://www.asciiart.eu/miscellaneous/playing-cards
 * Author: ejm98
 */
class CliCardDrawer {

    fun drawCards(vararg cards: Card) = drawCards(cards.toList())

    fun drawCards(cards: List<Card>): String {
        val sb = StringBuilder()
        val cardCount = cards.size
        val cardTemplates = cards.map { CardTemplate.from(it) }
        for (representationLineIndex in CardTemplate.representationLineRange) {
            for (cardIndex in 0 until cardCount) {
                val cardDrawing = when (representationLineIndex) {
                    CardTemplate.LINE_RANK_LABEL_FIRST -> cardTemplates[cardIndex]
                            .representationLines[representationLineIndex].format(cardRankDisplayUpper(cards[cardIndex]))
                    CardTemplate.LINE_RANK_LABEL_LAST -> cardTemplates[cardIndex]
                            .representationLines[representationLineIndex].format(cardRankDisplayLower(cards[cardIndex]))
                    else -> cardTemplates[cardIndex].representationLines[representationLineIndex]
                }
                sb.append(cardDrawing)
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    /**
     * Formats upper card rank display
     * Will format display as a two symbol part.
     * ex: 9 == "9 "; 10 == "10"
     */
    internal fun cardRankDisplayUpper(card: Card): String {
        if (card.rank.out.count() > 1) {
            return card.rank.out
        }
        return "${card.rank.out} "
    }

    /**
     * Formats lower card rank display
     * Will format display as a two symbol part.
     * ex: 9 == "_9"; 10 == "10"
     */
    internal fun cardRankDisplayLower(card: Card): String {
        if (card.rank.out.count() > 1) {
            return card.rank.out
        }
        return "_${card.rank.out}"
    }

    enum class CardTemplate(
            val representationLines: List<String>
    ) {
        SPADE(listOf(
                " _______ ",
                "|%s .   |",
                "|  /.\\  |",
                "| (_._) |",
                "|   |   |",
                "|_____%s|"
        )),
        DIAMOND(listOf(
                " _______ ",
                "|%s ^   |",
                "|  / \\  |",
                "|  \\ /  |",
                "|   .   |",
                "|_____%s|"
        )),
        CLUB(listOf(
                " _______ ",
                "|%s _   |",
                "|  ( )  |",
                "| (_'_) |",
                "|   |   |",
                "|_____%s|"
        )),
        HEART(listOf(
                " _______ ",
                "|%s_ _  |",
                "| ( v ) |",
                "|  \\ /  |",
                "|   .   |",
                "|_____%s|"
        ))
        ;

        companion object {
            const val MAX_LINES = 6
            const val LINE_FIRST = 0
            const val LINE_LAST = MAX_LINES - 1
            const val LINE_RANK_LABEL_FIRST = 1
            const val LINE_RANK_LABEL_LAST = MAX_LINES - 1

            val representationLineRange = 0 until MAX_LINES

            fun from(card: Card): CardTemplate = when(card.suite) {
                CardSuite.SPADE -> SPADE
                CardSuite.DIAMOND -> DIAMOND
                CardSuite.HEART -> HEART
                CardSuite.CLUB -> CLUB
            }
        }

    }

}