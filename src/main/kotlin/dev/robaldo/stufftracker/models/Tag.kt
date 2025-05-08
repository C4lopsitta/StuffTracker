package dev.robaldo.stufftracker.models

import kotlinx.serialization.Serializable


@Serializable
data class Tag(
    val uid: String,
    val name: String
)
