package lt.markmerkk

import lt.markmerkk.actions.*
import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.actions.system.ActionSystem
import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Game
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.TurnsManager
import java.util.*

fun main(args: Array<String>) {
    // Game components
    val players = listOf(
            Player("Marius"),
            Player("Enrika")
    )
    val turnsManager = TurnsManager(players)
    val game = Game(Card.generateDeck(), players, turnsManager)

    // Cli control components
    val inputReader = Scanner(System.`in`)
    val actionExecutorSystem = ActionExecutorSystem()
    val actionExecutorGame = ActionExecutorGame()
    val cliInputHandler = CliInputHandler(
            actionTranslators = listOf(
                    ActionTranslatorQuit(players),
                    ActionTranslatorCards(players)
            )
    )

    println("Hello and welcome to game of 'Stupid'!")
    while (!game.isGameOver) {
        game.runTurn()
        val inputAction = cliInputHandler.handleInput(inputReader.nextLine())
        when (inputAction) {
            is ActionGame -> actionExecutorGame.execute(inputAction)
            is ActionSystem -> actionExecutorSystem.execute(inputAction)
            else -> println("[WARN] Action cannot be executed")
        }
    }
}