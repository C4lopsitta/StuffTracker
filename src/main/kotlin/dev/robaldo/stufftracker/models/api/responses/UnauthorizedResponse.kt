package dev.robaldo.stufftracker.models.api.responses

data class UnauthorizedResponse (
    val error: String = "Unauthorized",
)