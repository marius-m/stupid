package lt.markmerkk.stupid.services

import lt.markmerkk.Utils
import lt.markmerkk.durak.Player

/**
 * Provides game instances
 */
class GameService {

    private var gameMap: MutableMap<String, GameWebInstance> = mutableMapOf()

    fun games(): List<GameWebInstance> = gameMap.values.toList()

    fun createNewGameInstance(): String {
        val gameId = Utils.generateUuid()
        gameMap[gameId] = GameWebInstance(
                id = gameId,
                players = listOf(
                        Player(name = "Marius", id = Utils.generateUuid()),
                        Player(name = "Enrika", id = Utils.generateUuid())
                )
        )
        return gameId
    }

}