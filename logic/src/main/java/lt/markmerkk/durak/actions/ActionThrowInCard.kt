package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Player

data class ActionThrowInCard(
        override val actionIssuer: Player,
        val thrownCard: Card
): ActionGame