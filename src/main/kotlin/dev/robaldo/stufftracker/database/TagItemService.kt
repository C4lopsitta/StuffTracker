package dev.robaldo.stufftracker.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

class TagItemService(database: Database): BaseService<Pair<String, String>> {
    object DbTable : Table("tagItemRelations") {
        val id = integer("id").autoIncrement()
        val item = reference("itemUid", ItemsService.DbTable.uid, onDelete = ReferenceOption.CASCADE)
        val tag = reference("tagId", TagsService.DbTable.id, onDelete = ReferenceOption.CASCADE)
    }

    override suspend fun create(item: Pair<String, String>): String {
        TODO("Not yet implemented")
    }

    override suspend fun read(uid: String): Pair<String, String>? {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<Pair<String, String>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Pair<String, String>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(uid: String, item: Pair<String, String>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Pair<String, String>): String {
        TODO("Not yet implemented")
    }

}