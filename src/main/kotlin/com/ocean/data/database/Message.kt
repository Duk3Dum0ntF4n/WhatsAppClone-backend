package com.ocean.data.database

import com.ocean.data.database.exposed.ExposedMessage
import com.ocean.data.dto.MessageDTO
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


object Message : Table("messages") {

    private val id = Message.integer("id")
    private val sender = Message.varchar("sender", 25)
    private val receiver = Message.varchar("receiver", 25)
    private val text = Message.varchar("text", 500)

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun createMessage(message: ExposedMessage): Unit = dbQuery {
        Message.insert {
            it[sender] = message.sender
            it[receiver] = message.receiver
            it[text] = message.text
        }
    }
}