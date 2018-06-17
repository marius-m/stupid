package lt.markmerkk.durak

import lt.markmerkk.durak.actions.ActionGame
import java.util.*

class Game(
        cards: List<Card>,
        private val players: List<Player>,
        private val turnsManager: TurnsManager
) {
    private val refillingDeck: RefillingDeck
    private val playingTable: PlayingTable

    init {
        players.forEach { it.cardsInHand = emptyList() }
        refillingDeck = RefillingDeck(cards = ArrayDeque<Card>(cards.toMutableList().shuffled()))
        playingTable = PlayingTable(cards = emptyList())
        players.forEach { it.refill(refillingDeck) }
    }

    var isGameOver = false

    fun runTurn() {
        println("Players: $players")
        println("Refilling deck : $refillingDeck")
        println("Playing table : $playingTable")
        println("Game status: ${turnsManager.attackingPlayer.name} turn")
    }

    fun inputTurn(action: ActionGame) {
        println("Entered action $action")
    }
}