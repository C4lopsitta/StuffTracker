package dev.robaldo.stufftracker.database

import dev.robaldo.stufftracker.enums.ShelfType
import dev.robaldo.stufftracker.models.Shelf
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

class ShelvesService(database: Database): BaseService<Shelf> {
    object DbTable : Table("shelves") {
        val uid = varchar("uid", 36)
        val number = integer("number")
        val type = enumeration("type", ShelfType::class)
        val usedFor = varchar("usedFor", 36).index("shelvesUsedFor").nullable()
        val storage = reference("storageUid", StoragesService.DbTable.uid, ReferenceOption.CASCADE)

        override val primaryKey = PrimaryKey(uid)
    }

    override suspend fun create(item: Shelf): String {
        TODO("Not yet implemented")
    }

    override suspend fun read(uid: String): Shelf? {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<Shelf> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Shelf) {
        TODO("Not yet implemented")
    }

    override suspend fun update(uid: String, item: Shelf) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Shelf): String {
        TODO("Not yet implemented")
    }
}