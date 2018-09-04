package lt.markmerkk.actions

import lt.markmerkk.Consts
import lt.markmerkk.durak.Game
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.durak.actions.ActionThrowInCard
import org.slf4j.LoggerFactory

class ActionExecutorGame(
        private val players: List<Player>,
        private val game: Game
) {
    fun execute(action: ActionGame) {
        return when (action) {
            is ActionThrowInCard -> game.throwCard(action)
            else -> { logger.warn("Action cannot be defined") }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }

}