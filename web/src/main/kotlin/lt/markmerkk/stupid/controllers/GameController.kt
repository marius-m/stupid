package lt.markmerkk.stupid.controllers

import lt.markmerkk.CliCardDrawer
import lt.markmerkk.durak.Player
import lt.markmerkk.stupid.entities.responses.ViewModelCard
import lt.markmerkk.stupid.entities.responses.ViewModelPlayerStatus
import lt.markmerkk.stupid.services.GameService
import org.springframework.web.bind.annotation.*


@RestController
class GameController(
        private val gameService: GameService
) {

    private val cardDisplay: CliCardDrawer = CliCardDrawer()

    @RequestMapping(
            value = arrayOf("/api/game/player/{game_id}/{player_id}"),
            method = arrayOf(RequestMethod.GET)
    )
    @ResponseBody
    fun playerStatus(
            @PathVariable("game_id") gameId: String,
            @PathVariable("player_id") playerId: String
    ): ViewModelPlayerStatus {
        if (!isGameValid(gameId, playerId)) {
            throw IllegalArgumentException("Game or player does not exist")
        }
        val player: Player = gameService.gameMap[gameId]!!.playerById(playerId)!!
        return ViewModelPlayerStatus(
                name = player.name,
                cards = player.cardsInHand().map { ViewModelCard.from(it) },
                cardDisplayInline = cardDisplay.drawCards(player.cardsInHand()),
                cardDisplayAsList = player.cardsInHand().map { cardDisplay.drawCards(it) }
        )
    }

    //region Convenience

    private fun isGameValid(
            gameId: String,
            playerId: String
    ): Boolean {
        if (!gameService.gameMap.containsKey(gameId)) {
            return false
        }
        val game = gameService.gameMap[gameId]!!
        if (!game.isPlayerIdValid(playerId)) {
            return false
        }
        return true
    }

    //endregion

}