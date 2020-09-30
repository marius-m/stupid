package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TurnsManagerInitTest : AbsTurnsManagerTest() {

    @Test(expected = IllegalStateException::class)
    fun noPlayers() {
        // Assemble
        // Act
        // Assert
        TurnsManager(emptyList())
    }

    @Test
    fun valid() {
        // Assemble
        // Act
        // Assert
        assertThat(turnsManager2Player.attackingPlayer).isNotNull()
        assertThat(turnsManager2Player.defendingPlayer).isNotNull()
        assertThat(turnsManager2Player.attackingPlayer).isNotEqualTo(turnsManager2Player.defendingPlayer)
    }

}