package lt.markmerkk.stupid.entities

data class RequestAction(
        var actionAsString: String = ""
) {
    constructor() : this(
            actionAsString = ""
    )
}
