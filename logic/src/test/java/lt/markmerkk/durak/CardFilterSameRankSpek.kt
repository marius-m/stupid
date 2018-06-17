package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object CardFilterSameRankSpek: Spek({
    given("filtering works properly") {
        on("no items in the filter") {
            val cardsToFilter: List<Card> = emptyList()
            val resultOfFilter = cardsToFilter.filterSameRank(CardRank.JACK)
            it("should have nothing to filter out") {
                assertThat(resultOfFilter).isEmpty()
            }
        }

        on("one filterable item") {
            val cardsToFilter: List<Card> = listOf(
                    Card(CardSuite.SPADE, CardRank.ACE),
                    Card(CardSuite.SPADE, CardRank.KING),
                    Card(CardSuite.SPADE, CardRank.QUEEN),
                    Card(CardSuite.SPADE, CardRank.JACK)
            )
            val resultOfFilter = cardsToFilter.filterSameRank(CardRank.JACK)
            it("should have nothing to filter out") {
                assertThat(resultOfFilter).containsExactly(Card(CardSuite.SPADE, CardRank.JACK))
            }
        }

        on("no filterable items") {
            val cardsToFilter: List<Card> = listOf(
                    Card(CardSuite.SPADE, CardRank.ACE),
                    Card(CardSuite.SPADE, CardRank.KING),
                    Card(CardSuite.SPADE, CardRank.QUEEN)
            )
            val resultOfFilter = cardsToFilter.filterSameRank(CardRank.JACK)
            it("should have nothing to filter out") {
                assertThat(resultOfFilter).isEmpty()
            }
        }
    }
})

