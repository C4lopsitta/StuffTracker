package dev.robaldo.stufftracker.models

import kotlinx.serialization.Serializable


@Serializable
data class Tag(
    val id: Int,
    val name: String
)
