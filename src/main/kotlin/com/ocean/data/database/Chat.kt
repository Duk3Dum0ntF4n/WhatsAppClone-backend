package com.ocean.data.database

import com.ocean.data.database.exposed.ExposedChat
import com.ocean.data.dto.ChatDTO
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

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
        Chat.insert {
            it[user1] = chat.user2
            it[user2] = chat.user1
        }
    }

    suspend fun allChats(username: String): List<ChatDTO>? {
        return try {
            dbQuery {
                Chat.select { Chat.user1 eq username }
                    .map {
                        ChatDTO(
                            username = it[user2],
                            id = it[id].toString()
                        )
                    }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}