package dev.robaldo.stufftracker.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

class UserGroupRelationService(database: Database): BaseService<Pair<String, String>> {
    object DbTable : Table("userGroupRelations") {
        val id = integer(name = "id").autoIncrement()
        val user = reference("userUid", UsersService.DbTable.uid, onDelete = ReferenceOption.CASCADE)
        val group = reference("usergroupUid", UserGroupsService.DbTable.uid, onDelete = ReferenceOption.CASCADE)

        override val primaryKey = PrimaryKey(id)
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