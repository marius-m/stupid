package lt.markmerkk

import lt.markmerkk.actions.*
import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.actions.system.ActionSystem
import lt.markmerkk.durak.*
import java.util.*

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

    println("Hello and welcome to game of 'Stupid'!")
    while (!game.isGameOver) {
        printGameStatus(players, playingTable, turnsManager, cliCardDrawer)
        val inputAction = cliInputHandler.handleInput(inputReader.nextLine())
        when (inputAction) {
            is ActionGame -> actionExecutorGame.execute(inputAction)
            is ActionSystem -> actionExecutorSystem.execute(inputAction)
            else -> println("[WARN] Action cannot be executed")
        }
    }

}

fun printGameStatus(
        players: List<Player>,
        playingTable: PlayingTable,
        turnsManager: TurnsManager,
        cliCardDrawer: CliCardDrawer
) {
    players.forEach {
        print("${it.name}'s cards (${it.cardsInHandSize()}): \n")
        print(cliCardDrawer.drawCards(it.cardsInHand()))
        print("\n")
    }
    print("\n--- Cards on table --- \n ")
    print(cliCardDrawer.drawCards(playingTable.allAttackingCards()))
    print(cliCardDrawer.drawCards(playingTable.allDefendingCards()))
    print("\n------- \n")
    print("Game status: ${turnsManager.attackingPlayer.name} turn to attack\n")
}