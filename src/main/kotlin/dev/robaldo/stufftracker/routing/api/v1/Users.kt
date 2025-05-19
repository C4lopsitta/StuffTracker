package dev.robaldo.stufftracker.routing.api.v1

import dev.robaldo.stufftracker.UserPrincipal
import dev.robaldo.stufftracker.database.UsersService
import dev.robaldo.stufftracker.enums.UserRole
import dev.robaldo.stufftracker.models.api.responses.InternalServerError
import dev.robaldo.stufftracker.models.api.responses.UnauthorizedResponse
import dev.robaldo.stufftracker.models.api.responses.NotFoundResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database

/**
 * Handles the /api/v1/users API paths.
 *
 * @see [UsersService]
 * @author Simone Robaldo
 */
fun Application.configureApiV1UsersRoutes(usersService: UsersService) {
    val rootPath = "/api/v1/users"

    routing {
        authenticate("bearer") {
            get("$rootPath/{uid}") {
                // Get requested uid and authenticated user
                val userUid = call.parameters["uid"]
                val principal = call.authentication.principal<UserPrincipal>()!!

                if(userUid == null) {
                    call.respond( HttpStatusCode.NotFound, NotFoundResponse("Could not find requested user $userUid", principal.newToken) )
                    return@get
                }

                // Requested user is authenticated user
                if(principal.user.uid == userUid) {
                    call.respond(principal.user)
                    return@get
                }

                // requested user is requested by admin
                if(principal.user.role == UserRole.ADMIN) {
                    val requestedUser = usersService.read(userUid)
                    if(requestedUser == null) {
                        call.respond( HttpStatusCode.NotFound, NotFoundResponse("Could not find requested user $userUid", principal.newToken) )
                        return@get
                    }

                    call.respond(requestedUser)
                    return@get
                }

                call.respond( HttpStatusCode.InternalServerError, InternalServerError() )
            }

            put("$rootPath/{uid}") {
                val userUid = call.parameters["uid"]
                val principal = call.authentication.principal<UserPrincipal>()!!

                
            }
        }
    }
}