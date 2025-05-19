package dev.robaldo.stufftracker.templates

import dev.robaldo.stufftracker.templates.components.TabEntry
import dev.robaldo.stufftracker.templates.components.navBar
import io.ktor.server.html.*
import kotlinx.html.*

class Home(private val selectedPath: String): Template<HTML> {
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
        body {
            navBar(TabEntry.getTabs(selectedPath))
            main {
                classes = setOf("container", "m-3")
                h1 { +"Stuff Tracker" }
                div {
                    classes = setOf("row", "gap-3")
                    a {
                        href = "/items/add"
                        classes = setOf("btn", "btn-primary", "col-auto")
                        +"Add an Item"
                    }
                    a {
                        href = "/archives/add"
                        classes = setOf("btn", "btn-secondary", "col-auto")
                        +"Add a Disc Archive"
                    }
                    a {
                        href = "/items"
                        classes = setOf("btn", "btn-secondary", "col-auto")
                        +"View items"
                    }
                }
            }
        }
    }
}