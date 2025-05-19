package dev.robaldo.stufftracker.routing.api.v1

import dev.robaldo.stufftracker.database.UsersService
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureApiV1(usersService: UsersService) {
    configureApiV1UsersRoutes(usersService)
}
