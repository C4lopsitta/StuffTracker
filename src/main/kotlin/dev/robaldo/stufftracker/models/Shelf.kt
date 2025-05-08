package dev.robaldo.stufftracker.models

import dev.robaldo.stufftracker.enums.ShelfType
import kotlinx.serialization.Serializable

@Serializable
data class Shelf(
    val uid: String,
    val number: Int,
    val type: ShelfType,
    val storage: Storage
)
