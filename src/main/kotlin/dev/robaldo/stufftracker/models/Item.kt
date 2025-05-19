package dev.robaldo.stufftracker.models

import kotlinx.serialization.Serializable


@Serializable
data class Item(
    val uid: String? = null,
    var name: String? = null,
    var description: String? = null,
    var owner: User? = null,
    var group: UserGroup? = null,
    var storageLocation: Shelf? = null,
    var canBeCheckedOut: Boolean = true,
    var tags: List<Tag> = emptyList(),
    var isCheckedOut: Boolean = false
)
