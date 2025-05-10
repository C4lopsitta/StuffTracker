package dev.robaldo.stufftracker.models

import dev.robaldo.stufftracker.enums.UserRole
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
data class User(
    val uid: String,
    val name: String,
    val surname: String,
    val email: String,
    @Transient
    val password: String = "",
    val role: UserRole,
    val groups: List<UserGroup> = listOf(),
)
