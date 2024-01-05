package com.ocean.database

import com.ocean.connection.remote.UserResponseRemote
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object User: Table("user") {

    private val username = varchar("username", 25)

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun findUser(username: String) : UserResponseRemote? {
        return dbQuery {
            User.select { User.username eq username }
                .map {
                    UserResponseRemote(it[User.username])
                }
                .singleOrNull()
        }
    }

}