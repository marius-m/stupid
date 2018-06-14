package lt.markmerkk.actions.system

class ActionSystemQuit : ActionSystem {
    override fun execute() {
        println("[INFO] Exiting game")
        System.exit(0)
    }
}