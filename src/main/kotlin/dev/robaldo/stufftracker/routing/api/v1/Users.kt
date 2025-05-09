package dev.robaldo.stufftracker.routing.api.v1

import dev.robaldo.stufftracker.UserPrincipal
import dev.robaldo.stufftracker.database.UsersService
import dev.robaldo.stufftracker.models.api.responses.Error401
import dev.robaldo.stufftracker.models.api.responses.Error404
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database

fun Application.configureApiV1UsersRoutes(database: Database) {
    val rootPath = "/api/v1/users"

    val usersService = UsersService(database)

    routing {
        authenticate("bearer") {
            get("$rootPath/{uid}") {
                val userUid = call.parameters["uid"]
                val principal = call.authentication.principal<UserPrincipal>()!!

                val authenticatedUser = usersService.read(principal.userUid)

                if(authenticatedUser == null) {
                    call.respond(HttpStatusCode.Unauthorized, Error401())
                }

                if(userUid == null) {
                    call.respond(HttpStatusCode.NotFound, Error404("Could not find requested user $userUid", principal.newToken))
                }

                if(authenticatedUser!!.uid == userUid) {
                    
                }
            }
        }

    }
}