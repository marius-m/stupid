package lt.markmerkk.durak.actions

/**
 * Action to finish round as the defence was successful
 */
class ActionFinishRound: ActionGame {
    override fun execute() { }
    override fun equals(other: Any?): Boolean = other != null && other is ActionFinishRound
    override fun hashCode(): Int = javaClass.hashCode()
}