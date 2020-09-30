package lt.markmerkk.actions.system

import lt.markmerkk.durak.Player

data class ActionSystemQuit(
        val actionIssuer: Player? = null
) : ActionSystem