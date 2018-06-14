package lt.markmerkk.actions.system

class ActionEmpty : ActionSystem {
    override fun execute() {
        println("[INFO] Invalid action!")
    }
}