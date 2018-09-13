package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Player

data class ActionThrowInCard(
        override val actionIssuer: Player,
        val thrownCard: Card
): ActionGame {
    override val actionTrigger: String = "${actionIssuer.name} throw ${thrownCard.valueAsString()}"
    override val actionUseDescription: String = "Throw ${thrownCard.valueAsString()}"
}