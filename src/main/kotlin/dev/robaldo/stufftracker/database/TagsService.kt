package dev.robaldo.stufftracker.database

import dev.robaldo.stufftracker.models.Tag
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

class TagsService(database: Database): BaseService<Tag> {
    object DbTable : Table("tags") {
        val id = integer("id").autoIncrement()
        val name = varchar("name", length = 64)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(DbTable)
        }
    }

    override suspend fun create(item: Tag): String {
        TODO("Not yet implemented")
    }

    override suspend fun read(uid: String): Tag? {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<Tag> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Tag) {
        TODO("Not yet implemented")
    }

    override suspend fun update(uid: String, item: Tag) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Tag): String {
        TODO("Not yet implemented")
    }

}