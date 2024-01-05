package com.ocean.connection.routing

import com.ocean.connection.ConnectionController
import com.ocean.connection.remote.MessageReceiveRemote
import com.ocean.connection.session.MessengerSession
import com.ocean.database.Message
import com.ocean.database.exposed.ExposedMessage
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json

fun Application.configureMessageRouting() {
    routing {
        messageSocketRoute(connectionController = ConnectionController())
        getAllMessages()
    }
}

fun Route.messageSocketRoute(connectionController: ConnectionController) {
    webSocket("/message-socket") {
        val session = call.sessions.get<MessengerSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        try {
            connectionController.onConnect(
                username = session.username,
                sessionId = session.sessionId,
                socket = this
            )
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val decodedString = Json.decodeFromString<MessageReceiveRemote>(frame.readText())
                    connectionController.sendMessage(
                        username = session.username,
                        text = decodedString.text,
                        chatId = decodedString.chatId
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.Conflict)
        } finally {
            connectionController.tryDisconnect(session.username)
        }
    }
}

fun Route.getAllMessages() {
    get("/messages/{id}") {
        val chatId = call.parameters["id"]
            ?: return@get call.respond(HttpStatusCode.BadRequest)
        call.respond(Message.getChatMessages(chatId))
    }
}