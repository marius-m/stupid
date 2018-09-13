package lt.markmerkk.stupid.entities.responses

import lt.markmerkk.CliCardDrawer
import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.PlayingCardPair
import lt.markmerkk.durak.PlayingTable
import lt.markmerkk.durak.actions.ActionGame
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

data class ViewModelGameDetails(
        val gameId: String,
        val playerId: String
)

data class ViewModelPlayer(
        val id: String,
        val name: String
)

data class ViewModelCard(
        val suite: String,
        val rank: String,
        val trump: Boolean,
        val display: String
) {
    companion object {
        fun from(card: Card): ViewModelCard = ViewModelCard(
                suite = card.suite.name,
                rank = card.rank.name,
                trump = card.isTrump,
                display = card.valueAsString()
        )
    }
}

data class ViewModelPlayerStatus(
        val name: String,
        val cards: List<ViewModelCard>,
        val cardDisplayInline: String,
        val cardDisplayAsList: List<String>
) {
    companion object {
        fun from(
                player: Player,
                cardDrawer: CliCardDrawer
        ): ViewModelPlayerStatus = ViewModelPlayerStatus(
                name = player.name,
                cardDisplayInline = cardDrawer.drawCards(player.cardsInHand()),
                cardDisplayAsList = player.cardsInHand().map { cardDrawer.drawCards(it) },
                cards = player.cardsInHand().map { ViewModelCard.from(it) }
        )
    }
}

data class ViewModelPlayerActions(
        val actions: List<ViewModelPlayerAction>
) {

    data class ViewModelPlayerAction(
            val description: String,
            val trigger: String
    )

    companion object {
        fun from(availableAction: List<ActionGame>): ViewModelPlayerActions = ViewModelPlayerActions(
                actions = availableAction.map {
                    ViewModelPlayerAction(
                            description = it.actionUseDescription,
                            trigger = it.actionTrigger
                    )
                }
        )
    }

}

data class ViewModelTable(
        val attackingCards: List<ViewModelCard>,
        val attackingCardsDisplayInline: String,
        val defendingCards: List<ViewModelCard>,
        val defendingCardsDisplayInline: String
) {

    companion object {
        fun from(
                attackingCards: List<Card>,
                defendingCards: List<Card>,
                cardDrawer: CliCardDrawer
        ): ViewModelTable = ViewModelTable(
                attackingCards = attackingCards.map { ViewModelCard.from(it) },
                attackingCardsDisplayInline = cardDrawer.drawCards(attackingCards),
                defendingCards = defendingCards.map { ViewModelCard.from(it) },
                defendingCardsDisplayInline = cardDrawer.drawCards(defendingCards)
        )
    }

}

