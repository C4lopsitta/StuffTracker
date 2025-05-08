package dev.robaldo.stufftracker.database

import dev.robaldo.stufftracker.models.UserGroup
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table

class UserGroupsService(database: Database): BaseService<UserGroup> {
    object DbTable : Table("usergroups") {
        val uid = varchar("uid", 36)
        val name = varchar("name", length = 64)

        override val primaryKey = PrimaryKey(uid)
    }

    override suspend fun create(item: UserGroup): String {
        TODO("Not yet implemented")
    }

    override suspend fun read(uid: String): UserGroup? {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<UserGroup> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: UserGroup) {
        TODO("Not yet implemented")
    }

    override suspend fun update(uid: String, item: UserGroup) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: UserGroup): String {
        TODO("Not yet implemented")
    }

}