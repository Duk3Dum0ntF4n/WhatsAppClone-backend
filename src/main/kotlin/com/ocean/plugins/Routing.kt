package com.ocean.plugins

import com.ocean.data.database.Chat
import com.ocean.data.database.exposed.ExposedChat
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        allUserChatsRoute()
        addChatRoute()
    }
}

fun Route.allUserChatsRoute() {
    get("/chat/{username}") {
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

fun Route.addChatRoute() {
    get("/chat/create/{username}") {
        val username = call.parameters["username"]
            ?: return@get call.respondText(
                "missing username",
                status = HttpStatusCode.BadRequest
            )
        Chat.createChat(
            ExposedChat(
                user1 = "test_1",
                user2 = username
            )
        )
        call.respondText(
            "chat created successfully",
            status = HttpStatusCode.OK
        )
    }
}