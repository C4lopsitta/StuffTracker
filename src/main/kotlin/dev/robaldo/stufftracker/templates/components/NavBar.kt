package dev.robaldo.stufftracker.templates.components

import io.ktor.server.html.*
import kotlinx.html.*

data class TabEntry(
    val label: String,
    val href: String = "#",
    var isActive: Boolean = false,
    val isDisabled: Boolean = false
) {
    companion object {
        fun getTabs(selctedPath: String): List<TabEntry> {
            val tabs = standardTabs

            tabs.forEach { tab ->
                tab.isActive = tab.href == selctedPath
            }

            return tabs
        }

        val standardTabs = listOf(
            TabEntry("Home", "/"),
            TabEntry("Items", "/items"),
            TabEntry("Rooms", "/rooms"),
            TabEntry("Your Checkouts", "/user/checkouts"),
            TabEntry("Disc Archives", "/archives"),
            TabEntry("Search", "/search"),
            TabEntry("User", "/user"),
        )
    }

}

fun FlowContent.navBar(tabs: List<TabEntry>) {
    ul("nav nav-tabs") {
        for (tab in tabs) {
            li("nav-item") {
                a(tab.href, classes = "nav-link".let {
                    when {
                        tab.isActive -> "$it active"
                        tab.isDisabled -> "$it disabled"
                        else -> it
                    }
                }) {
                    if (tab.isActive) {
                        attributes["aria-current"] = "page"
                    }
                    if (tab.isDisabled) {
                        attributes["aria-disabled"] = "true"
                        attributes["tabindex"] = "-1" // Optional for better UX
                    }
                    +tab.label
                }
            }
        }
    }
}
