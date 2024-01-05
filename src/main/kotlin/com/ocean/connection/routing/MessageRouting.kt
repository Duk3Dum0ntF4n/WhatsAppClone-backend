package com.ocean.connection.routing

import com.ocean.connection.remote.MessageReceiveRemote
import com.ocean.database.Message
import com.ocean.database.exposed.ExposedMessage
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureMessageRouting() {
    routing {
        addMessageRoute()
    }
}

fun Route.addMessageRoute() {
    post("/message/add}") {
        val message = call.receive<MessageReceiveRemote>()
        val exposedMessage = ExposedMessage(
            username = "test_1",
            text = message.text,
            chat_id = message.chat_id
        )
        Message.createMessage(exposedMessage)
        call.respond(HttpStatusCode.OK)
    }
}
