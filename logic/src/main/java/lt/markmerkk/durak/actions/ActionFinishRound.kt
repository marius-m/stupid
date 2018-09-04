package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Player

/**
 * Action to finish round as the defence was successful
 */
data class ActionFinishRound(
        override val actionIssuer: Player
) : ActionGame {
    override val actionUseDescription: String = "${actionIssuer.name} finish round"
}