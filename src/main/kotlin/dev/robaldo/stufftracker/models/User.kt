package dev.robaldo.stufftracker.models

import dev.robaldo.stufftracker.enums.UserRole
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
data class User(
    val uid: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    @Transient
    val password: String = "",
    val role: UserRole? = null,
    val groups: List<UserGroup> = listOf(),
)
