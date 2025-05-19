package dev.robaldo.stufftracker.database

import dev.robaldo.stufftracker.enums.UserRole
import dev.robaldo.stufftracker.models.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

class UsersService(database: Database): BaseService<User> {
    object DbTable : Table("users") {
        val uid = varchar("uid", 36)
        val name = varchar("name", length = 64)
        val surname = varchar("surname", length = 64)
        val email = varchar("email", length = 128)
        val password = varchar("password", length = 128)
        val role = enumeration("role", UserRole::class)

        override val primaryKey = PrimaryKey(uid)
    }

    init {
        transaction(database) {
            SchemaUtils.create(DbTable)
        }
    }

    override suspend fun create(item: User): String {
        TODO("Not yet implemented")
    }

    override suspend fun read(uid: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: User) {
        TODO("Not yet implemented")
    }

    override suspend fun update(uid: String, item: User) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: User): String {
        TODO("Not yet implemented")
    }
}