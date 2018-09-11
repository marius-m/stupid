package lt.markmerkk.stupid.services

import lt.markmerkk.Utils
import lt.markmerkk.durak.Player

/**
 * Provides game instances
 */
class GameService {

    private var gameMap: MutableMap<String, GameWebInstance> = mutableMapOf()

    fun createNewGameInstance() {
        val gameId = Utils.generateUuid()
        gameMap[gameId] = GameWebInstance(
                players = listOf(
                        Player(name = "Marius", id = Utils.generateUuid()),
                        Player(name = "Enrika", id = Utils.generateUuid())
                )
        )
    }

}