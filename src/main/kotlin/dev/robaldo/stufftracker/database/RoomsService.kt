package dev.robaldo.stufftracker.database

import dev.robaldo.stufftracker.models.Room
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table

class RoomsService(database: Database): BaseService<Room> {
    object DbTable : Table("rooms") {
        val uid = varchar("uid", 36)
        val name = varchar("name", length = 64)

        override val primaryKey = PrimaryKey(uid)
    }

    override suspend fun create(item: Room): String {
        TODO("Not yet implemented")
    }

    override suspend fun read(uid: String): Room? {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<Room> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Room) {
        TODO("Not yet implemented")
    }

    override suspend fun update(uid: String, item: Room) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Room): String {
        TODO("Not yet implemented")
    }

}