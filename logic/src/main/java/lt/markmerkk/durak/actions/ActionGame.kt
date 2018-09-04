package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Player

/**
 * Defines an action to be executed by the game interface
 */
interface ActionGame : Action {
    val actionIssuer: Player
    val actionUseDescription: String
}