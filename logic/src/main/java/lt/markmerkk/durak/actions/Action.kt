package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Player

interface Action {

    /**
     * Source where action was executed
     */
    val actionIssuer: Player?
    fun execute()
}