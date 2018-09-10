package lt.markmerkk.actions

import lt.markmerkk.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.Action
import lt.markmerkk.durak.actions.ActionTakeAllCards
import java.util.regex.Pattern

/**
 * Responsible for parsing card throwing actions
 */
class ActionTranslatorTakeAll(
        private val players: List<Player> = emptyList()
) : ActionTranslator {

    private val playerPatterns: Map<Player, Pattern> = players
            .map { Pair(it, Pattern.compile("(${it.name}) take all")) }
            .toMap()

    override fun translateAction(actionAsString: String): Action {
        playerPatterns.forEach { (player, pattern) ->
            val matcher = pattern.matcher(actionAsString)
            if (matcher.find()) {
                return ActionTakeAllCards(actionIssuer = player)
            }
        }
        return ActionIllegalCannotTranslate(actionAsString)
    }

}