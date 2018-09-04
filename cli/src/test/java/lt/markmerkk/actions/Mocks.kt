package lt.markmerkk.actions

import lt.markmerkk.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.Action

class FakeTranslatorNoAction() : ActionTranslator {
    override fun translateAction(actionAsString: String): Action {
        return ActionIllegalCannotTranslate(actionAsString)
    }
}

class FakeTranslatorValid(val outputAction: Action) : ActionTranslator {
    override fun translateAction(actionAsString: String): Action {
        return outputAction
    }
}

class FakeAction1 : Action {
//    override val actionIssuer: Player? = null
//    override fun execute() {}
}

class FakeAction2 : Action {
//    override val actionIssuer: Player? = null
//    override fun execute() {}
}
