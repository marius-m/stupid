package lt.markmerkk.actions

import lt.markmerkk.actions.system.ActionSystem

class ActionExecutorSystem {
    fun execute(action: ActionSystem) {
        println("[INFO] Executing system action: $action")
        action.execute()
    }
}