package dev.robaldo.stufftracker.database

import dev.robaldo.stufftracker.models.Checkout
import kotlinx.datetime.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

class CheckoutsService(database: Database): BaseService<Checkout> {
    object DbTable : Table("checkouts") {
        val uid = varchar("uid", 36)
        val start = datetime("start").default(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
        val end = datetime("end").nullable()
        val plannedEnd = datetime("plannedEnd").nullable()
        val item = reference("itemuid", ItemsService.DbTable.uid, onDelete = ReferenceOption.CASCADE).index("checkoutItem")
        val user = reference("userUid", UserGroupsService.DbTable.uid, onDelete = ReferenceOption.CASCADE).index("checkoutUser")

        override val primaryKey = PrimaryKey(uid)
    }

    override suspend fun create(item: Checkout): String {
        TODO("Not yet implemented")
    }

    override suspend fun read(uid: String): Checkout? {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<Checkout> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Checkout) {
        TODO("Not yet implemented")
    }

    override suspend fun update(uid: String, item: Checkout) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Checkout): String {
        TODO("Not yet implemented")
    }

}