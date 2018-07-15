package lt.markmerkk.actions

import lt.markmerkk.durak.actions.Action

interface ActionTranslator {
    fun translateAction(
            actionAsString: String
    ): Action
}