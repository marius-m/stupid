package lt.markmerkk.actions

import lt.markmerkk.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.actions.system.ActionSystemQuit
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.Action
import java.util.regex.Pattern

class ActionTranslatorQuit(
        private val players: List<Player> = emptyList()
): ActionTranslator {

    private val playerPatterns: Map<Player, Pattern> = players
            .map { Pair(it, Pattern.compile("${it.name} quit"))  }
            .toMap()

    override fun translateAction(
            actionAsString: String
    ): Action {
        if (actionAsString == "quit") {
            return ActionSystemQuit(actionIssuer = null)
        }
        playerPatterns.forEach { (player, pattern) ->
            val matcher = pattern.matcher(actionAsString)
            if (matcher.find()) {
                return ActionSystemQuit(actionIssuer = player)
            }
        }
        return ActionIllegalCannotTranslate(actionAsString)
    }
}
