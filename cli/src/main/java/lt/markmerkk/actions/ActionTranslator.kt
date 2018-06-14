package lt.markmerkk.actions

import lt.markmerkk.actions.system.ActionEmpty
import lt.markmerkk.actions.system.ActionSystemQuit

class ActionTranslator {
    fun translateAction(actionAsString: String): Action {
        if (actionAsString == "quit") {
            return ActionSystemQuit()
        }
        return ActionEmpty()
    }
}
