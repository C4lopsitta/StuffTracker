package dev.robaldo.stufftracker

import dev.robaldo.stufftracker.redis.Redis
import io.ktor.server.application.*
import io.lettuce.core.RedisClient
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

    configureSerialization()
    configureDatabases(database)
    configureSecurity()
    configureRouting()
}
