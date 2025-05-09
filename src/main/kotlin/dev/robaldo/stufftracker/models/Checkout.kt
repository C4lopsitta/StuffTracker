package dev.robaldo.stufftracker.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class Checkout(
    val uid: String,
    val item: Item,
    val user: User,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val plannedEnd: LocalDateTime? = null,
)
