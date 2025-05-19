package dev.robaldo.stufftracker.routing.api

import dev.robaldo.stufftracker.database.UsersService
import dev.robaldo.stufftracker.routing.api.v1.configureApiV1
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureApi(usersService: UsersService) {
    configureApiV1(usersService)
}