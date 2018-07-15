package lt.markmerkk.actions.system

import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.Action

/**
 * Defines an action that was translated incorrectly.
 * Input was parsed into multiple actions, not sure which path should it take.
 */
data class ActionIllegalMultipleActions(
        private val inputString: String = "",
        private val translatedActions: List<Action> = emptyList()
) : ActionSystem {
    override val actionIssuer: Player? = null
    override fun execute() {
        println("[WARN] Illegal action! Multiple translations received! Input $inputString was translated into $translatedActions")
    }
}