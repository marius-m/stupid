package lt.markmerkk.actions.system

/**
 * When action cannot be executed
 */
data class ActionIllegalCannotTranslate(
        private val inputAsString: String = "",
        private val detailDescription: String = ""
) : ActionSystem