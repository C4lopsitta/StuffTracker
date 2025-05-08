package dev.robaldo.stufftracker.models

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val uid: String,
    val name: String
)
