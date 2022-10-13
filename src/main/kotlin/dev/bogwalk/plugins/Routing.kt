package dev.bogwalk.plugins

import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        static("/static") {
            resources("files")
        }
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", null))
        }
        get("/top") {
            call.respond(FreeMarkerContent("recent.ftl", null))
        }
        get("random") {
            call.respond(FreeMarkerContent("random.ftl", null))
        }
    }
}