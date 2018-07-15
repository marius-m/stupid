package lt.markmerkk.actions.system

import lt.markmerkk.durak.Player

data class ActionSystemQuit(
        override val actionIssuer: Player? = null
) : ActionSystem {
    override fun execute() {
        if (actionIssuer != null) {
            print("[INFO] ${actionIssuer.name} has quit the game!")
        } else {
            print("[INFO] Exit was issued!")
        }
        System.exit(0)
    }
}