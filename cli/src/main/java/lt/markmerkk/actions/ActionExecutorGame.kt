package lt.markmerkk.actions

import lt.markmerkk.actions.game.ActionGame

class ActionExecutorGame {
    fun execute(action: ActionGame) {
        println("[INFO] Executing game action: $action")
        action.execute()
    }
}