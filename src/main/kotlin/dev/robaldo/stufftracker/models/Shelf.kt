package dev.robaldo.stufftracker.models

import dev.robaldo.stufftracker.enums.ShelfType
import kotlinx.serialization.Serializable

@Serializable
data class Shelf(
    val uid: String? = null,
    val number: Int? = null,
    val type: ShelfType? = null,
    val usedFor: String? = null,
    val storage: Storage? = null,
)
