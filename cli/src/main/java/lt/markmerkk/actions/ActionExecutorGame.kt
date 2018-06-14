package lt.markmerkk.actions

import lt.markmerkk.durak.actions.ActionGame

class ActionExecutorGame {
    fun execute(action: ActionGame) {
        println("[INFO] Executing game action: $action")
        action.execute()
    }
}