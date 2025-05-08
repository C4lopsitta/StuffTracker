package dev.robaldo.stufftracker.models

import kotlinx.serialization.Serializable


@Serializable
data class UserGroup(
    val uid: String,
    val name: String,
    val users: List<User>
)
