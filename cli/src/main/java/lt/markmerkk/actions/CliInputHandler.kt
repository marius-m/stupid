package lt.markmerkk.actions

import lt.markmerkk.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.actions.system.ActionIllegalMultipleActions
import lt.markmerkk.durak.actions.Action

class CliInputHandler(
        private val actionTranslators: List<ActionTranslator>
) {
    fun handleInput(
            inputAsString: String
    ): Action {
        val possibleActions = actionTranslators.map { it.translateAction(inputAsString) }
                .filter { it !is ActionIllegalCannotTranslate }
        if (possibleActions.size > 1) {
            return ActionIllegalMultipleActions(inputAsString, possibleActions)
        }
        if (possibleActions.isEmpty()) {
            return ActionIllegalCannotTranslate(inputAsString)
        }
        return possibleActions.first()
    }
}