package com.ocean.plugins

import com.ocean.data.database.Chat
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        allUserChatsRoute()
    }
}

fun Route.allUserChatsRoute() {
    get("/chats/{username}") {
        val username = call.parameters["username"]
            ?: return@get call.respondText(
                "MissingChat",
                status = HttpStatusCode.BadRequest
            )
        val chats = Chat.allChats(username)
            ?: return@get call.respondText(
                "No such username",
                status = HttpStatusCode.NotFound
            )
        call.respond(chats)
    }
}