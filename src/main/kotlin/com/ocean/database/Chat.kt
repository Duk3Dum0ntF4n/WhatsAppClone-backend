package com.ocean.database

import com.ocean.database.exposed.ExposedChat
import com.ocean.connection.remote.ChatResponseRemote
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.union

object Chat : Table("chat") {

    private val user1 = Chat.varchar("user1", 25)
    private val user2 = Chat.varchar("user2", 25)
    private val id = Chat.integer("id")

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun createChat(chat: ExposedChat): Unit = dbQuery {
        Chat.insert {
            it[user1] = chat.user1
            it[user2] = chat.user2
        }
    }

    suspend fun getUserChats(username: String): List<ChatResponseRemote> {
        return dbQuery {
            Chat.select {
                user1 eq username
            }
                .union(Chat.select {
                    user2 eq username
                })
                .map {
                    ChatResponseRemote(
                        username = if (it[user1] == username) {
                            it[user2]
                        } else {
                            it[user1]
                        },
                        chatId = it[id].toString()
                    )
                }
        }
    }
}