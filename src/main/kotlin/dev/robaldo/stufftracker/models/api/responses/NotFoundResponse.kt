package dev.robaldo.stufftracker.models.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class NotFoundResponse(
    val error: String = "Not Found",
    val newToken: String? = null
)
