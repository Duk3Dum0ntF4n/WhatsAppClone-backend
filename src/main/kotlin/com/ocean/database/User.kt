package com.ocean.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object User: Table("user") {

    private val username = varchar("username", 25)

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun ifUserNotExist(username: String) : Boolean {
        return dbQuery {
            User.select { User.username eq username }
                .singleOrNull() == null
        }
    }
}