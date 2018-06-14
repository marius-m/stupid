package lt.markmerkk.durak

import lt.markmerkk.durak.actions.ActionGame

class Game(
        private val players: List<Player>,
        private val turnsManager: TurnsManager
) {
    var isGameOver = false

    fun runTurn() {
        println("Game status: ")
    }

    fun inputTurn(action: ActionGame) {
        println("Entered action $action")
    }
}