package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Card

data class ActionThrowInCard(
        val thrownCard: Card
): ActionGame {
    override fun execute() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}