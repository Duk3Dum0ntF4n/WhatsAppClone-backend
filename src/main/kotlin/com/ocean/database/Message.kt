package com.ocean.database

import com.ocean.database.exposed.ExposedMessage
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


object Message : Table("message") {

    private val username = Message.varchar("username", 25)
    private val text = Message.varchar("text", 500)
    private val chatId = Message.integer("chat_id")
    private val id = Message.integer("id")

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun getChatMessages(receivedId: String) : List<ExposedMessage> {
        return dbQuery {
            Message.select {
                chatId eq receivedId.toInt()
            }.map {
                ExposedMessage(
                    username = it[username],
                    text = it[text],
                    chatId = receivedId
                )
            }
        }
    }

    suspend fun createMessage(message: ExposedMessage): Unit = dbQuery {
        Message.insert {
            it[username] = message.username
            it[text] = message.text
            it[chatId] = message.chatId.toInt()
        }
    }
}