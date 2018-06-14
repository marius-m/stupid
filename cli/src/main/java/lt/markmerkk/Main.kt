package lt.markmerkk

import lt.markmerkk.actions.ActionExecutorGame
import lt.markmerkk.actions.ActionExecutorSystem
import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.actions.ActionTranslator
import lt.markmerkk.actions.system.ActionSystem
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
    val game = Game(players, turnsManager)

    // Cli control components
    val inputReader = Scanner(System.`in`)
    val actionTranslator = ActionTranslator()
    val actionExecutorSystem = ActionExecutorSystem()
    val actionExecutorGame = ActionExecutorGame()

    println("Hello and welcome to game of 'Stupid'!")
    while (!game.isGameOver) {
        game.runTurn()
        val inputAction = actionTranslator.translateAction(inputReader.next())
        when (inputAction) {
            is ActionGame -> actionExecutorGame.execute(inputAction)
            is ActionSystem -> actionExecutorSystem.execute(inputAction)
            else -> println("[WARN] Action cannot be executed")
        }
    }
}