package dev.robaldo.stufftracker.models

import kotlinx.serialization.Serializable


@Serializable
data class Item(
    val uid: String,
    val name: String,
    val description: String? = null,
    val owner: User? = null,
    val group: UserGroup? = null,
    val storageLocation: Shelf,
    val canBeCheckedOut: Boolean = true,
    val tags: List<Tag> = emptyList()
)
