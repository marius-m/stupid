package lt.markmerkk.durak

import java.util.*

/**
 * Responsible for defining who's turn is it
 * todo: Terrible naming, change later when came up with
 */
class TurnsManager(
        players: List<Player>
) {

    var players = players
    var attackingPlayer: Player
    var defendingPlayer: Player

    init {
        if (players.size < 2) {
            throw IllegalStateException("Need at least two players to play the game")
        }
        attackingPlayer = players.first()
        defendingPlayer = nextOf(attackingPlayer)
    }

    fun nextTurn() {
        attackingPlayer = defendingPlayer
        defendingPlayer = nextOf(attackingPlayer)
    }

    /**
     * Returns next player in the list
     */
    private fun nextOf(player: Player): Player {
        val currentPlayerIndex = players.indexOf(player)
        val nextPlayerIndex = if (currentPlayerIndex + 1 < players.size) currentPlayerIndex + 1 else 0
        return players.get(nextPlayerIndex)
    }


}