package dev.robaldo.stufftracker.database

import dev.robaldo.stufftracker.enums.StorageType
import dev.robaldo.stufftracker.models.Storage
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

class StoragesService(database: Database): BaseService<Storage> {
    object DbTable : Table() {
        val uid = varchar("uid", 36)
        val name = varchar("name", length = 64)
        val type = enumeration("type", StorageType::class)
        val room = reference("roomUid", RoomsService.DbTable.uid, ReferenceOption.CASCADE)

        override val primaryKey = PrimaryKey(uid)
    }

    override suspend fun create(item: Storage): String {
        TODO("Not yet implemented")
    }

    override suspend fun read(uid: String): Storage? {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<Storage> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Storage) {
        TODO("Not yet implemented")
    }

    override suspend fun update(uid: String, item: Storage) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Storage): String {
        TODO("Not yet implemented")
    }
}