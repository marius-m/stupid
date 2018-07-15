package lt.markmerkk.actions

import lt.markmerkk.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.actions.system.ActionIllegalMultipleActions
import lt.markmerkk.actions.system.ActionSystem
import lt.markmerkk.actions.system.ActionSystemQuit

class ActionExecutorSystem {
    fun execute(action: ActionSystem) {
        return when (action) {
            is ActionIllegalCannotTranslate -> println("[INFO] Invalid action: $action")
            is ActionIllegalMultipleActions -> println("[INFO] Invalid action: $action")
            is ActionSystemQuit -> {
                if (action.actionIssuer != null) {
                    print("[INFO] ${action.actionIssuer.name} has quit the game!")
                } else {
                    print("[INFO] Exit was issued!")
                }
                System.exit(0)
            }
            else -> {
                println("[WARN] Cannot figure action")
            }
        }
    }
}