package dev.robaldo.stufftracker.database

import com.sun.jna.platform.win32.DBT
import dev.robaldo.stufftracker.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ItemsService(database: Database): BaseService<Item> {
    object DbTable : Table("items") {
        val uid = varchar("uid", 36)
        val name = varchar("name", length = 64).index("itemName")
        val description = text("description").nullable()
        val storageLocation = reference("shelfUid", ShelvesService.DbTable.uid, ReferenceOption.CASCADE)
        val canBeCheckedOut = bool("canBeCheckedOut").default(true)
        val owner = reference("ownerUid", UsersService.DbTable.uid, onDelete = ReferenceOption.SET_NULL).nullable()
        val group = reference("groupOwnerUid", UserGroupsService.DbTable.uid, onDelete = ReferenceOption.SET_NULL).nullable()

        override val primaryKey = PrimaryKey(uid)
    }

    init {
        transaction(database) {
            SchemaUtils.create(DbTable)
        }
    }

    override suspend fun create(item: Item): String = dbQuery {
        DbTable.insert {
            it[uid] = item.uid ?: UUID.randomUUID().toString()
            it[name] = item.name!!
            it[description] = item.description
            it[canBeCheckedOut] = item.canBeCheckedOut
            it[owner] = item.owner?.uid
            it[group] = item.group?.uid
            it[storageLocation] = item.storageLocation!!.uid!!
            it[canBeCheckedOut] = item.canBeCheckedOut
        }[DbTable.name]
    }

    override suspend fun read(uid: String): Item? {
        val item = dbQuery {
            DbTable
                .selectAll()
                .where(DbTable.uid eq uid)
                .map {
                    Item(
                        uid = it[DbTable.uid],
                        name = it[DbTable.name],
                        description = it[DbTable.description],
                        canBeCheckedOut = it[DbTable.canBeCheckedOut],
                        owner = User(uid = it[DbTable.owner]),
                        group = UserGroup(it[DbTable.group]),
                        tags = emptyList(),
                        storageLocation = Shelf(uid = it[DbTable.storageLocation])
                    )
                }
                .singleOrNull()
        }

        if (item == null) return null

        item.isCheckedOut = isItemCheckedOut(item)
        item.tags = getItemTags(item)

        // TODO)) User Owner and Group

        return item
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

    suspend fun getItemCount(): Long = dbQuery {
        ItemsService.DbTable.selectAll().count() ?: 0
    }

    private suspend fun isItemCheckedOut(item: Item): Boolean = isItemCheckedOut(item.uid!!)

    private suspend fun isItemCheckedOut(itemUid: String): Boolean = dbQuery {
        CheckoutsService.DbTable
            .select(CheckoutsService.DbTable.uid eq itemUid)
            .orderBy(CheckoutsService.DbTable.start, SortOrder.DESC)
            .limit(1)
            .map { it[CheckoutsService.DbTable.end] == null }
            .singleOrNull() ?: false
    }

    private suspend fun getItemTags(item: Item): List<Tag> = getItemTags(item.uid!!)

    private suspend fun getItemTags(uid: String): List<Tag> = dbQuery {
            (TagItemService.DbTable innerJoin TagsService.DbTable)
                .select(TagsService.DbTable.id, TagsService.DbTable.name)
                .where(TagItemService.DbTable.item.eq(uid))
                .map {
                    Tag(id = it[TagsService.DbTable.id], name = it[TagsService.DbTable.name])
                }
    }

    suspend fun readPaged(limit: Int, offset: Long): List<Item> {
        val items = dbQuery {
            DbTable
                .selectAll()
                .limit(limit)
                .offset(offset)
                .map {
                    Item(
                        uid = it[DbTable.uid],
                        name = it[DbTable.name],
                        description = it[DbTable.description],
                    )
                }
        }

        items.forEach { item ->
            item.tags = getItemTags(item)
            item.isCheckedOut = isItemCheckedOut(item)
        }

        return items
    }
}