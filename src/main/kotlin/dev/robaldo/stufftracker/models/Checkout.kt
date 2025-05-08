package dev.robaldo.stufftracker.models

import kotlinx.serialization.Serializable


@Serializable
data class Checkout(
    val uid: String,
    val item: Item,
    val user: User,
    val start: Unit,
    val end: Unit
)
