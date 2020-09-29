package lt.markmerkk.stupid.services

import lt.markmerkk.durak.*
import lt.markmerkk.durak.actions.*
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.system.ActionIllegalMultipleActions
import org.slf4j.LoggerFactory

data class GameWebInstance(
        val id: String,
        val players: List<Player>
) {

    val game: Game
    private val actionExecutorGame: ActionExecutorGame
    private val cliInputHandler: CliInputHandler

    init {
        val turnsManager = TurnsManager(players = players)
        game = Game(
                cards = Card.generateDeckSmall(),
                players = players,
                turnsManager = turnsManager
        )
        game.resetGame()
        game.refillPlayerCards()


        actionExecutorGame = ActionExecutorGame(players, game)
        cliInputHandler = CliInputHandler(
                actionTranslators = listOf(
                        ActionTranslatorThrowCards(players),
                        ActionTranslatorTakeAll(players),
                        ActionTranslatorFinishRound(players)
                )
        )
    }

    fun handleAction(actionAsString: String) {
        val inputAction = cliInputHandler.handleInput(actionAsString)
        when (inputAction) {
            is ActionIllegalMultipleActions -> logger.info("Illegal action!")
            is ActionIllegalCannotTranslate -> logger.info("Illegal action!")
            is ActionGame -> actionExecutorGame.execute(inputAction)
            else -> logger.warn("Action cannot be executed\n")
        }
    }

    fun playerById(playerId: String): Player? = players.find { it.id == playerId }

    /**
     * Checks if player exist
     */
    fun isPlayerIdValid(playerId: String): Boolean {
        return players
                .filter { it.id == playerId }
                .count() > 0
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }

}