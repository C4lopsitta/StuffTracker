package dev.robaldo.stufftracker.models

import dev.robaldo.stufftracker.enums.StorageType
import kotlinx.serialization.Serializable


@Serializable
data class Storage(
    val uid: String,
    val name: String,
    val type: StorageType,
    val room: Room
)
