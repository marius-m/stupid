package lt.markmerkk.durak.actions

/**
 * Action to take all cards in, as the defence was not able to complete
 */
class ActionTakeAllCards: ActionGame {
    override fun execute() { }
    override fun equals(other: Any?): Boolean = other != null && other is ActionTakeAllCards
    override fun hashCode(): Int = javaClass.hashCode()
}