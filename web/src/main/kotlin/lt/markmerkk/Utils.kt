package lt.markmerkk

import java.util.*


object Utils {

    fun generateUuid(): String {
        val source = "lt.markmerkk.stupid"
        val bytes = source.toByteArray(charset("UTF-8"))
        val uuid = UUID.nameUUIDFromBytes(bytes)
        return uuid.toString()
    }

}