package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Player

/**
 * Action to take all cards in, as the defence was not able to complete
 */
data class ActionTakeAllCards(
        override val actionIssuer: Player
) : ActionGame