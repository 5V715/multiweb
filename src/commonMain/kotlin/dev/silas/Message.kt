package dev.silas

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Message(val desc: String, val priority: Int) {
    val id: Int = Random.nextInt(Int.MAX_VALUE)

    companion object {
        const val path = "/messages"
    }
}