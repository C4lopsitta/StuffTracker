package dev.robaldo.stufftracker.templates

import dev.robaldo.stufftracker.models.Item
import dev.robaldo.stufftracker.templates.components.TabEntry
import dev.robaldo.stufftracker.templates.components.navBar
import io.ktor.server.html.*
import kotlinx.html.*

class ItemEditor(private val selectedPath: String, private val item: Item? = null): Template<HTML> {
    override fun HTML.apply() {
        head {
            link {
                rel = "stylesheet"
                href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css"
                integrity = "sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT"
                attributes["crossorigin"] = "anonymous"
            }
            link {
                rel = "stylesheet"
                href = "https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
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
            form {
                method = FormMethod.post
                action = if( item == null ) "/items/add" else "/items/${item.uid!!}"
                encType = FormEncType.applicationXWwwFormUrlEncoded
                classes = setOf("container", "m-3")

                div {
                    classes = setOf("d-flex", "justify-content-between", "align-items-center", "mb-3")
                    h1 {
                        +if( item == null ) "New Item" else "Edit Item"
                    }
                    div {
                        button {
                            classes = setOf("btn", "btn-primary")
                            type = ButtonType.submit
                            +if( item == null) "Create" else "Save Edits"
                        }
                    }
                }

                div("mb-3 row") {
                    if( item == null ) {
                        div("mb-3 col-12 col-lg-4") {
                            label("form-label") {
                                htmlFor = "uid"
                                +"UID"
                            }
                            textInput(classes = "form-control") {
                                id = "uid"
                                name = "uid"
                                value = "UID will be generated on creation"
                                disabled = true
                            }
                        }
                    } else {
                        div("mb-3 col-12 col-lg-4") {
                            label("form-label") {
                                htmlFor = "uid"
                                +"UID"
                            }
                            div("input-group") {
                                textInput(classes = "form-control") {
                                    id = "uid"
                                    name = "uid"
                                    value = item.uid!!
                                    disabled = true
                                }
                                button(classes = "btn btn-outline-secondary", type = ButtonType.button) {
                                    attributes["data-bs-toggle"] = "modal"
                                    attributes["data-bs-target"] = "#code-dialog"
                                    i("bi bi-qr-code") {}

                                }
                            }
                        }
                    }

                    // TODO
                    div("mb-3 col-12 col-lg-4") {
                        label("form-label") {
                            htmlFor = "ownerUid"
                            +"Owner (user)"
                        }
                        select("form-select") {
                            id = "ownerUid"
                            name = "ownerUid"
                            required = true

                            option {
                                value = "null"
                                selected = true
                                +"Public (for group or everyone)"
                            }
                        }
                    }

                    // TODO
                    div("mb-3 col-12 col-lg-4") {
                        label("form-label") {
                            htmlFor = "groupUid"
                            +"Group Ownership"
                        }
                        select("form-select") {
                            id = "groupUid"
                            name = "groupUid"
                            required = true

                            option {
                                value = "null"
                                selected = true
                                +"No Group Ownership"
                            }
                        }
                    }
                }

                div("mb-3") {
                    label("form-label") {
                        htmlFor = "name"
                        +"Name (required)"
                    }
                    textInput(classes = "form-control") {
                        id = "name"
                        name = "name"
                        required = true
                        value = if( item == null ) "" else item.name!!
                    }
                }

                div("mb-3") {
                    label("form-label") {
                        htmlFor = "description"
                        +"Description"
                    }
                    textArea(classes = "form-control") {
                        id = "description"
                        name = "description"
                        rows = "4"
                        +if( item == null ) "" else item.description ?: ""
                    }
                }

                div("form-check mb-3") {
                    checkBoxInput(classes = "form-check-input") {
                        id = "canBeCheckedOut"
                        name = "can_be_checked_out"
                        checked = item?.canBeCheckedOut ?: true
                        required = true
                    }
                    label("form-check-label") {
                        htmlFor = "canBeCheckedOut"
                        +"Can be checked out"
                    }
                    div("form-text mb-3") {
                        +"When checked, the item will be able to be checked out by allowed users."
                        br {}
                        + "Allowed users are defined as the owner or users in the same group, if neither are set, it'll be owned by anyone."
                    }
                }
            }

            if( item != null ) {
                div("modal fade") {
                    id = "code-dialog"
                    attributes["tabindex"] = "-1"
                    attributes["aria-labelledby"] = "Aztec Code Dialog"
                    attributes["aria-hidden"] = "true"

                    div("modal-dialog") {
                        div("modal-content") {
                            div("modal-header") {
                                h3(classes = "modal-title") {
                                    +"Aztec Code"
                                }
                                button(type = ButtonType.button, classes = "btn-close") {
                                    attributes["data-bs-dismiss"] = "modal"
                                    attributes["aria-label"] = "Close"
                                }
                            }

                            div("modal-body d-flex justify-content-center align-items-center flex-column") {
                                h5 { +item.name!! }
                                canvas {
                                    id = "code-canvas"
                                }
                                p("mt-4") { +item.uid!! }

                                script {
                                    src = "https://unpkg.com/bwip-js/dist/bwip-js-min.js"
                                }

                                script {
                                    unsafe {
                                        +"""
                                        try {
                                            bwipjs.toCanvas('code-canvas', {
                                                bcid:        'azteccode',       // Barcode type
                                                text:        '${item.uid!!}',  // Your UUID-4
                                                scale:       3,                 // 3x scaling factor
                                                version:     0,                 // Automatic version selection
                                                includetext: false              // No human-readable text
                                            });
                                        } catch (e) {
                                            console.error('Error generating Aztec Code:', e);
                                        }
                                    """.trimIndent()
                                    }
                                }
                            }

                            div("modal-footer") {
                                button(type = ButtonType.button, classes = "btn btn-secondary") {
                                    attributes["data-bs-dismiss"] = "modal"
                                    +"Close"
                                }
                                button(type = ButtonType.button, classes = "btn btn-primary") {
                                    id = "code-download-btn"
                                    +"Download Aztec Code"
                                }

                                script {
                                    unsafe {
                                        +"""
                                        document.getElementById('code-download-btn')?.addEventListener('click', function () {
                                            const canvas = document.getElementById('code-canvas');
                                            if (!canvas) return;
                    
                                            const link = document.createElement('a');
                                            link.download = '${item.name!!}_${item.uid!!}_aztec-code.png';
                                            link.href = canvas.toDataURL('image/png');
                                            link.click();
                                        });
                                    """.trimIndent()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}