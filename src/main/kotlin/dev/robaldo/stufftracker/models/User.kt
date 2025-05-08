package dev.robaldo.stufftracker.models

import dev.robaldo.stufftracker.enums.UserRole
import kotlinx.serialization.Serializable


@Serializable
data class User(
    val uid: String,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: UserRole
)
