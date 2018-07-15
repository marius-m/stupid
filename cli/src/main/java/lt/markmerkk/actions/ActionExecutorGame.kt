package lt.markmerkk.actions

import lt.markmerkk.durak.Game
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.durak.actions.ActionThrowInCard

class ActionExecutorGame(
        private val players: List<Player>,
        private val game: Game
) {
    fun execute(action: ActionGame) {
        println("[INFO] Executing game action: $action")
        when (action) {
            is ActionThrowInCard -> {
                println("[INFO] Trying to throw in card ${action.thrownCard} by ${action.actionIssuer}")
            }
            else -> {
                println("[WARN] Action cannot be defined")
            }
        }.javaClass
    }
}