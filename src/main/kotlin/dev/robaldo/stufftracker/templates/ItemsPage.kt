package dev.robaldo.stufftracker.templates

import dev.robaldo.stufftracker.models.Item
import dev.robaldo.stufftracker.templates.components.TabEntry
import dev.robaldo.stufftracker.templates.components.navBar
import io.ktor.server.html.*
import kotlinx.html.*

class ItemsPage(
    private val selectedPath: String,
    private val pagedItems: List<Item>,
    private val currentPage: Long,
    private val itemsPerPage: Int,
    private val totalItemCount: Long
): Template<HTML> {
    override fun HTML.apply() {
        head {
            link {
                rel = "stylesheet"
                href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css"
                integrity = "sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT"
                attributes["crossorigin"] = "anonymous"
            }
            script {
                src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
                crossorigin = ScriptCrossorigin.anonymous
                integrity = "sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO"
            }
            meta { charset = "utf-8" }
            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1"
            }
            title { +"Stuff Tracker" }
        }
        body("w-100") {
            navBar(TabEntry.getTabs(selectedPath))
            main("container m-3 w-100") {
                div {
                    classes = setOf("d-flex", "justify-content-between", "align-items-center", "mb-3")
                    h1 {
                        +"Items"
                    }
                    div {
                        a {
                            classes = setOf("btn", "btn-primary")
                            href = "/items/add"
                            +"Add an Item"
                        }
                    }

                }
                table("table w-100 container-fluid") {
                    thead {
                        tr {
                            th { +"Name" }
                            th { +"Description" }
                            th { +"Tags" }
                            th { +"In Storage" }
                            th { +"" }
                        }
                    }
                    tbody {
                        for (item in pagedItems) {
                            tr {
                                td { +"${item.name}" }
                                td { +"${item.description}" }
                                td { +item.tags.map { tag -> tag.name }.toString() }
                                td { +if(item.isCheckedOut) "false" else "true" }
                                td {
                                    a {
                                        classes = setOf("btn", "btn-primary")
                                        href = "/items/${item.uid}"
                                        +"Edit"
                                    }
                                }
                            }
                        }
                    }
                }

                nav() {
                    attributes["aria-label"] = "page"
                    ul("pagination justify-content-center") {
                        li("page-item") {
                            a(classes = "page-link".let { if (currentPage == 1L) it.plus(" disabled") else it }) {
                                href = if(currentPage != 1L) "/items?items=${itemsPerPage}&page=${currentPage - 1}" else "#"
                                +"Previous"
                            }
                        }
                        if(currentPage >= 2) {
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = "/items?items=${itemsPerPage}&page=${currentPage - 1}"
                                    +"${currentPage - 1}"
                                }
                            }
                        }
                        li("page-item active") {
                            a(classes = "page-link") {
                                href = "#"
                                + "$currentPage"
                            }
                        }
                        if(currentPage < (totalItemCount / itemsPerPage)) {
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = "/items?items=${itemsPerPage}&page=${currentPage + 1}"
                                    +"${currentPage + 1}"
                                }
                            }
                        }
                        li("page-item") {
                            a(classes = "page-link".let { if (currentPage >= (totalItemCount / itemsPerPage)) it.plus(" disabled") else it }) {
                                href = if(currentPage < (totalItemCount / itemsPerPage)) "/items?items=${itemsPerPage}&page=${currentPage + 1}" else "#"
                                +"Next"
                            }
                        }
                    }
                }
            }
        }
    }
}