package dev.robaldo.stufftracker

import dev.robaldo.stufftracker.database.*
import dev.robaldo.stufftracker.redis.Redis
import dev.robaldo.stufftracker.routing.api.configureApi
import dev.robaldo.stufftracker.routing.web.itemsController
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.lettuce.core.RedisClient
import kotlinx.serialization.json.Json
import org.h2.tools.Server
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
//    Server.createTcpServer("-tcp", "-tcpAllowOthers", "-ifNotExists").start()

//    val database = Database.connect(
//        url = "jdbc:h2:tcp://localhost/mem:testdb;DB_CLOSE_DELAY=-1",
//        user = "root",
//        driver = "org.h2.Driver",
//        password = "",
//    )

    val database = Database.connect(
        url = "jdbc:mariadb://localhost:3306/stufftracker",
        user = "root",
        driver = "org.mariadb.jdbc.Driver",
        password = "rootpw",
    )

    install(ContentNegotiation) {
        json(Json{
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }

    configureSerialization()
    configureSecurity(database)
//    configureDatabases(database)

    val checkoutsService = CheckoutsService(database)
    val itemsService = ItemsService(database)
    val roomsService = RoomsService(database)
    val shelvesService = ShelvesService(database)
    val storagesService = StoragesService(database)
    val tagItemService = TagItemService(database)
    val tagsService = TagsService(database)
    val userGroupRelationService = UserGroupRelationService(database)
    val usersService = UsersService(database)
    val userGroupsService = UsersService(database)

    configureApi(usersService)

    // configure Webpages
    itemsController(itemsService)

//    configureRouting()
}
