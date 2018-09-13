package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Player

/**
 * Defines an action to be executed by the game interface
 */
interface ActionGame : Action {
    /**
     * Who triggers the action
     */
    val actionIssuer: Player

    /**
     * Action to trigger event
     */
    val actionTrigger: String

    /**
     * Description how to use the action
     */
    val actionUseDescription: String
}