package lt.markmerkk.actions.system

import lt.markmerkk.durak.Player

/**
 * When action cannot be executed
 */
data class ActionIllegalCannotTranslate(
        private val inputAsString: String = ""
) : ActionSystem {
    override val actionIssuer: Player? = null
    override fun execute() {
        println("[WARN] Invalid action! Cannot translate '$inputAsString'")
    }
}