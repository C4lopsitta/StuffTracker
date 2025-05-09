package dev.robaldo.stufftracker.models.api.responses

data class Error401 (
    val error: String = "Unauthorized",
)