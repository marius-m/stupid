package lt.markmerkk.stupid.entities.responses

import lt.markmerkk.durak.Player
import lt.markmerkk.stupid.services.GameWebInstance

data class ViewModelAllGames(
        val games: List<ViewModelGame>
) {

    data class ViewModelGame(
            val index: Int,
            val id: String,
            val players: List<ViewModelPlayer>
    )

    companion object {
        fun from(
                instances: List<GameWebInstance>
        ): ViewModelAllGames {
            val games = instances.mapIndexed { index, instance ->
                ViewModelGame(
                        index = index + 1, // as representation, we start from 1
                        id = instance.id,
                        players = instance.players.map { ViewModelPlayer(it.id, it.name) }
                )
            }
            return ViewModelAllGames(games = games)
        }
    }
}

data class ViewModelPlayer(
        val id: String,
        val name: String
)


