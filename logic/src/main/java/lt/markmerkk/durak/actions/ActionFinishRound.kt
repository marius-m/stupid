package lt.markmerkk.durak.actions

class ActionFinishRound: ActionGame {
    override fun execute() { }
    override fun equals(other: Any?): Boolean = other != null && other is ActionFinishRound
    override fun hashCode(): Int = javaClass.hashCode()
}