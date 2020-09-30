package lt.markmerkk.stupid.services

import lt.markmerkk.Utils
import lt.markmerkk.durak.Player

/**
 * Provides game instances
 */
class GameService {

    var gameMap: Map<String, GameWebInstance> = emptyMap()
        private set

    fun games(): List<GameWebInstance> = gameMap.values.toList()

    fun createNewGameInstance(): String {
        val gameId = Utils.generateUuid()
        val gameInstance = GameWebInstance(
                id = gameId,
                players = listOf(
                        Player(name = "Marius", id = Utils.generateUuid()),
                        Player(name = "Enrika", id = Utils.generateUuid())
                )
        )
        gameMap = gameMap.plus(gameId to gameInstance)
        return gameId
    }

}