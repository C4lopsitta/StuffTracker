package dev.robaldo.stufftracker

import dev.robaldo.stufftracker.database.UsersService
import dev.robaldo.stufftracker.models.User
import dev.robaldo.stufftracker.redis.Redis
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.jetbrains.exposed.sql.Database

data class UserPrincipal(val user: User, val newToken: String)

fun Application.configureSecurity(database: Database) {
    val usersService = UsersService(database)

    install(Authentication) {
        bearer("bearer") {
            realm = "StuffTracker"
            authenticate { token ->
                val newToken = Redis().use { redis ->
                    redis.validateAndRotateToken(token.token)
                }

                if(newToken.first != null) {
                    val authenticatedUser = usersService.read(newToken.first!!) ?: return@authenticate null

                    UserPrincipal(authenticatedUser, newToken.first!!)
                }

                return@authenticate null
            }
        }
    }
}
