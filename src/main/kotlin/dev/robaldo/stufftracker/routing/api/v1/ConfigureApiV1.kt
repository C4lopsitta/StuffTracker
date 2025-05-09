package dev.robaldo.stufftracker.routing.api.v1

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureApiV1(database: Database) {
    configureApiV1UsersRoutes(database)
}
