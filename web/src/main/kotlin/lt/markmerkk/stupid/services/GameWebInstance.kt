package lt.markmerkk.stupid.services

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Game
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.TurnsManager

data class GameWebInstance(
        val id: String,
        val players: List<Player>
) {
    private val game: Game

    init {
        val turnsManager = TurnsManager(players = players)
        game = Game(
                cards = Card.generateDeckSmall(),
                players = players,
                turnsManager = turnsManager
        )
        game.resetGame()
        game.refillPlayerCards()
    }

}