package dev.robaldo.stufftracker.database

import dev.robaldo.stufftracker.models.Item
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

class ItemsService(database: Database): BaseService<Item> {
    object DbTable : Table("items") {
        val uid = varchar("uid", 36)
        val name = varchar("name", length = 64)
        val description = text("description").nullable()
        val storageLocation = reference("shelfUid", ShelvesService.DbTable.uid, ReferenceOption.CASCADE)
        val owner = reference("ownerUid", UsersService.DbTable.uid, onDelete = ReferenceOption.SET_NULL).nullable()
        val group = reference("groupOwnerUid", UserGroupsService.DbTable.uid, onDelete = ReferenceOption.SET_NULL).nullable()

        override val primaryKey = PrimaryKey(uid)
    }

    override suspend fun create(item: Item): String {
        TODO()
    }

    override suspend fun read(uid: String): Item? {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<Item> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun update(uid: String, item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Item): String {
        TODO("Not yet implemented")
    }

}