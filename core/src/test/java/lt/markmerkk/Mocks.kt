package lt.markmerkk

import lt.markmerkk.durak.Player

object Mocks {

    fun createPlayer(
            name: String = "valid_player"
    ): Player {
        return Player(
                name = name
        )
    }

}