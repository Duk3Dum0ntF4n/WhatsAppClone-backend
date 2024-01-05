package com.ocean.database

import com.ocean.database.exposed.ExposedMessage
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


object Message : Table("messages") {

    private val id = Message.integer("id")
    private val username = Message.varchar("sender", 25)
    private val text = Message.varchar("text", 500)
    private val chat_id = Message.integer("chat_id")

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun createMessage(message: ExposedMessage): Unit = dbQuery {
        Message.insert {
            it[username] = message.username
            it[text] = message.text
            it[chat_id] = message.chat_id.toInt()
        }
    }
}