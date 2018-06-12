package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object GameSpek: Spek({
    given("a calculator") {
        val game = Game()

        on("addition") {
            val sum = 2+2
            it("should return the result of adding the first number to the second number") {
                assertThat(false).isFalse()
            }
        }
    }
})

