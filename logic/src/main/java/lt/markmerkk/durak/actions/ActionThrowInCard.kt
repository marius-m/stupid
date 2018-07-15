package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Player

data class ActionThrowInCard(
        override val actionIssuer: Player,
        val thrownCard: Card
): ActionGame {

    override fun execute() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}