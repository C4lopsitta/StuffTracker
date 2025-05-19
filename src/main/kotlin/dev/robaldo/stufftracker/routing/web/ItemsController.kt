package dev.robaldo.stufftracker.routing.web

import dev.robaldo.stufftracker.database.ItemsService
import dev.robaldo.stufftracker.models.Item
import dev.robaldo.stufftracker.models.Shelf
import dev.robaldo.stufftracker.templates.ItemEditor
import dev.robaldo.stufftracker.templates.Home
import dev.robaldo.stufftracker.templates.ItemsPage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.itemsController(itemsService: ItemsService) {
    routing {
        get("/") {
            call.respondHtmlTemplate(Home("/")) {}
        }

        get("/items") {
            val currentPage = call.request.queryParameters["page"]?.toLong() ?: 1
            val itemsPerPage = call.request.queryParameters["items"]?.toInt() ?: 25

            if(currentPage < 0) {
                call.respondText(status = HttpStatusCode.BadRequest, text = "Bad Request")
            }

            val pagedItems = itemsService.readPaged(itemsPerPage, (currentPage-1) * itemsPerPage)
            val totalItemCount = itemsService.getItemCount()

            call.respondHtmlTemplate(ItemsPage(
                selectedPath = "/items",
                pagedItems = pagedItems,
                currentPage = currentPage,
                itemsPerPage = itemsPerPage,
                totalItemCount = totalItemCount
            )) {}
        }

        get("/items/add") {
            call.respondHtmlTemplate(ItemEditor("/items")) {}
        }

        post("/items/add") {
            val inputAttributes = call.receiveParameters()

            val ownerUid = if(inputAttributes["ownerUid"] != null) inputAttributes["ownerUid"] else null
            val groupUid = if(inputAttributes["groupUid"] != null) inputAttributes["groupUid"] else null

            val newItem = Item(
                uid = UUID.randomUUID().toString(),
                name = inputAttributes["name"]!!,
                description = inputAttributes["description"],
                canBeCheckedOut = inputAttributes["canBeCheckedOut"]?.toBoolean() ?: true,
                storageLocation = Shelf(uid = "dbfc4c87-b5d3-4691-89e4-c341d5b759b3")
            )

            itemsService.create(newItem)

            call.respondRedirect("/items")
        }

        get("/items/{uid}") {
            val uid = call.parameters["uid"]!!
            val item = itemsService.read(uid)

            call.respondHtmlTemplate(ItemEditor("/items", item)) {}
        }
    }
}
