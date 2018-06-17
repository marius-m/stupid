package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.PlayingTable

/**
 * Defines possible game actions
 */
interface PossibleGameActionsFilter<out T: ActionGame> {

    /**
     * Filters possible actions
     */
    fun filterActions(
            player: Player,
            playingTable: PlayingTable
    ): List<T>
}