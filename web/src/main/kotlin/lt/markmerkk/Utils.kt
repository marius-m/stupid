package lt.markmerkk

import java.util.*


object Utils {

    fun generateUuid(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }

}