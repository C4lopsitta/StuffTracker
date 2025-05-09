package dev.robaldo.stufftracker

import dev.robaldo.stufftracker.redis.Redis
import dev.robaldo.stufftracker.routing.api.configureApi
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.lettuce.core.RedisClient
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )

    install(ContentNegotiation) {
        json(Json{
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }

    configureSerialization()
    configureSecurity()
    configureDatabases(database)

    configureApi(database)
//    configureRouting()
}
