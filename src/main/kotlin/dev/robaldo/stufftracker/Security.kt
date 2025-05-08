package dev.robaldo.stufftracker

import dev.robaldo.stufftracker.database.UsersService
import dev.robaldo.stufftracker.models.User
import dev.robaldo.stufftracker.redis.Redis
import io.ktor.server.application.*
import io.ktor.server.auth.*

data class UserPrincipal(val userUid: String, val newToken: String)

fun Application.configureSecurity() {
    install(Authentication) {
        bearer("bearer") {
            realm = "StuffTracker"
            authenticate { token ->
                val result = Redis().use { redis ->
                    redis.validateAndRotateToken(token.token)
                }

                if(result.first != null) {
                    UserPrincipal(result.second!!, result.first!!)
                }

                return@authenticate null
            }
        }
    }
}
