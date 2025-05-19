package dev.robaldo.stufftracker.models

import kotlinx.serialization.Serializable


@Serializable
data class UserGroup(
    val uid: String? = null,
    val name: String? = null,
    val users: List<User> = listOf()
)
