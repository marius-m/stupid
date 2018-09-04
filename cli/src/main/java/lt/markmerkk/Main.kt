package lt.markmerkk

import lt.markmerkk.actions.*
import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.actions.system.ActionSystem
import lt.markmerkk.durak.*
import org.slf4j.LoggerFactory
import java.util.Scanner

class Main {

    fun main(args: Array<String>) {
        // Game components
        val players = listOf(
                Player("Marius"),
                Player("Enrika")
        )
        val turnsManager = TurnsManager(players = players)
        val playingTable = PlayingTable(cards = emptyList())
        val game = Game(
                cards = Card.generateDeck(),
                players = players,
                turnsManager = turnsManager,
                playingTable = playingTable
        )
        game.resetGame()
        game.refillPlayerCards()

        // Cli control components
        val inputReader = Scanner(System.`in`)
        val actionExecutorSystem = ActionExecutorSystem()
        val actionExecutorGame = ActionExecutorGame(players, game)
        val cliInputHandler = CliInputHandler(
                actionTranslators = listOf(
                        ActionTranslatorQuit(players),
                        ActionTranslatorThrowCards(players)
                )
        )
        val cliCardDrawer = CliCardDrawer()

        logger.info("Hello and welcome to game of 'Stupid'!\n")
        while (!game.isGameOver) {
            printGameStatus(players, playingTable, turnsManager, cliCardDrawer)
            val inputAction = cliInputHandler.handleInput(inputReader.nextLine())
            when (inputAction) {
                is ActionGame -> actionExecutorGame.execute(inputAction)
                is ActionSystem -> actionExecutorSystem.execute(inputAction)
                else -> logger.warn("Action cannot be executed\n")
            }
        }

    }

    fun printGameStatus(
            players: List<Player>,
            playingTable: PlayingTable,
            turnsManager: TurnsManager,
            cliCardDrawer: CliCardDrawer
    ) {
        logger.info("\n--- Cards on table --- \n")
        logger.info(cliCardDrawer.drawCards(playingTable.allAttackingCards()))
        logger.info(cliCardDrawer.drawCards(playingTable.allDefendingCards()))
        logger.info("\n------- \n")
        players.forEach {
            logger.info("${it.name}'s cards (${it.cardsInHandSize()}): \n")
            logger.info(cliCardDrawer.drawCards(it.cardsInHand()))
            logger.info("\n")
        }
        logger.info("Game status: ${turnsManager.attackingPlayer.name} turn to attack\n")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }
}

fun main(args : Array<String>) {
    Main().main(args)
}
