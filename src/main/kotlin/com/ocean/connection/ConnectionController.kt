package com.ocean.connection

import com.ocean.connection.remote.ChatResponseRemote
import com.ocean.database.Chat
import com.ocean.database.Message
import com.ocean.database.User
import com.ocean.database.exposed.ExposedMessage
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class ConnectionController {

    private val members = ConcurrentHashMap<String, Member>()

    suspend fun onConnect(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.containsKey(username)) {
            throw Exception("Member already connected")
        }
        members[username] = Member(
            username = username,
            chatList = Chat.getUserChats(username),
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun ifUserNotExist(username: String): Boolean {
        return User.ifUserNotExist(username)
    }

    suspend fun sendMessage(username: String, text: String, chatId: String) {
        val message = ExposedMessage(username, text, chatId)
        Message.createMessage(message)
        members.values.forEach { member ->
            if (member.chatList.find { it.chatId == chatId } != null) {
                val parsedMessage = Json.encodeToString(message)
                member.socket.send(Frame.Text(parsedMessage))
            }
        }
    }

    suspend fun getMessages(chatId: String) : List<ExposedMessage> {
        return Message.getChatMessages(chatId)
    }

    suspend fun getChats(username: String): List<ChatResponseRemote> {
        return Chat.getUserChats(username)
    }

    suspend fun tryDisconnect(username: String) {
        members[username]?.socket?.close()
        if (members.containsKey(username)) {
            members.remove(username)
        }
    }

}