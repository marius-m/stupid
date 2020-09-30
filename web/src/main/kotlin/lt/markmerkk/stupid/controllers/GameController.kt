package lt.markmerkk.stupid.controllers

import lt.markmerkk.CliCardDrawer
import lt.markmerkk.durak.Game
import lt.markmerkk.durak.Player
import lt.markmerkk.stupid.entities.RequestAction
import lt.markmerkk.stupid.entities.responses.ViewModelAllGames
import lt.markmerkk.stupid.entities.responses.ViewModelPlayerActions
import lt.markmerkk.stupid.entities.responses.ViewModelPlayerStatus
import lt.markmerkk.stupid.entities.responses.ViewModelTable
import lt.markmerkk.stupid.services.GameService
import lt.markmerkk.stupid.services.GameWebInstance
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController("/api")
class GameController(
        private val gameService: GameService
) {

    private val cardDrawer: CliCardDrawer = CliCardDrawer()

    @RequestMapping(
            value = arrayOf("/games"),
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE)
    )
    @ResponseBody
    fun games(): ViewModelAllGames {
        return ViewModelAllGames.from(gameService.games())
    }

    @RequestMapping(
            value = arrayOf("/game/player/{game_id}/{player_id}"),
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE)
    )
    @ResponseBody
    fun playerStatus(
            @PathVariable("game_id") gameId: String,
            @PathVariable("player_id") playerId: String
    ): ViewModelPlayerStatus {
        if (!isStateValid(gameId, playerId)) {
            throw IllegalArgumentException("Game or player does not exist")
        }
        val player: Player = gameService.gameMap[gameId]!!.playerById(playerId)!!
        return ViewModelPlayerStatus.from(player, cardDrawer)
    }

    @RequestMapping(
            value = arrayOf("/game/actions/{game_id}/{player_id}"),
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE)
    )
    @ResponseBody
    fun playerActions(
            @PathVariable("game_id") gameId: String,
            @PathVariable("player_id") playerId: String
    ): ViewModelPlayerActions {
        if (!isStateValid(gameId, playerId)) {
            throw IllegalArgumentException("Game or player does not exist")
        }
        val game = gameService.gameMap[gameId]!!.game
        val player = gameService.gameMap[gameId]!!.playerById(playerId)!!
        return ViewModelPlayerActions.from(game.availablePlayerActions(player))
    }

    @RequestMapping(
            value = arrayOf("/game/table/{game_id}"),
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE)
    )
    @ResponseBody
    fun tableStatus(
            @PathVariable("game_id") gameId: String
    ): ViewModelTable {
        if (!isGameValid(gameId)) {
            throw IllegalArgumentException("Game does not exist")
        }
        val game = gameService.gameMap[gameId]!!.game
        return ViewModelTable.from(
                attackingCards = game.playingTable.allAttackingCards(),
                defendingCards = game.playingTable.allDefendingCards(),
                cardDrawer = cardDrawer
        )
    }

    @RequestMapping(
            value = arrayOf("/triggerAction/player/{game_id}/{player_id}"),
            method = arrayOf(RequestMethod.POST),
            consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE)
    )
    @ResponseStatus
    fun postAction(
            @PathVariable("game_id") gameId: String,
            @PathVariable("player_id") playerId: String,
            @RequestBody action: RequestAction
    ): ResponseEntity<String> {
        if (!isStateValid(gameId, playerId)) {
            throw IllegalArgumentException("Game or player does not exist")
        }
        val gameWebInstance: GameWebInstance = gameService.gameMap[gameId]!!
        gameWebInstance.handleAction(action.actionAsString)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    //region Convenience

    private fun isGameValid(
            gameId: String
    ): Boolean {
        return gameService.gameMap.containsKey(gameId)
    }

    private fun isStateValid(
            gameId: String,
            playerId: String
    ): Boolean {
        return gameService.gameMap.containsKey(gameId)
                && gameService.gameMap[gameId]!!.isPlayerIdValid(playerId)
    }

    //endregion

}