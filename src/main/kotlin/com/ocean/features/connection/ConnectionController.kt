package com.ocean.features.connection

import com.ocean.data.MessageDataSource
import io.ktor.websocket.WebSocketSession
import java.awt.SystemColor.text
import java.util.concurrent.ConcurrentHashMap

class ConnectionController (
    private val messageDataSource: MessageDataSource
) {

    //
    private val connections = ConcurrentHashMap<String, ConnectionUser> ()

    //Чт обудет происходить при подключении пользователя
    fun onConnect(
        login: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if(connections.containsKey(login)) {
            throw Exception("member already exist")
        }
        connections[login] = ConnectionUser(
            login = login,
            sessionId = sessionId,
            socket = socket
        )
    }

    //Что будет происходить при отправке сообщения
    fun sendMessage(senderLogin: String, message: String) {
        connections.forEach { connection ->

        }
    }

}