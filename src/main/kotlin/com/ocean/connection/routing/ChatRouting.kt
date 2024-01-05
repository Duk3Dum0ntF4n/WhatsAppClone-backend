package com.ocean.connection.routing

import com.ocean.database.Chat
import com.ocean.database.exposed.ExposedChat
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureChatRouting() {
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
        val chats = Chat.getUserChats(username)
        call.respond(chats)
    }
}

fun Route.addChatRoute() {
    get("/chat/add/{username}") {
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