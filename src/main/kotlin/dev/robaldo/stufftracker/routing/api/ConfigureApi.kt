package dev.robaldo.stufftracker.routing.api

import dev.robaldo.stufftracker.routing.api.v1.configureApiV1
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureApi(database: Database) {
    configureApiV1(database)
}