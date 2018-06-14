package lt.markmerkk.durak

class Game(
        private val players: List<Player>,
        private val turnsManager: TurnsManager
) {
    var isGameOver = false

    fun runTurn() {
        println("Game status: ")
    }

    fun inputTurn(action: String) {
        println("Entered action $action")
    }
}