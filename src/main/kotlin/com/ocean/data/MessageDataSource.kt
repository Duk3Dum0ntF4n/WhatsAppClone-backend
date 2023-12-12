package com.ocean.data

import com.ocean.data.model.MessageDTO

interface MessageDataSource {

    suspend fun getAllMessages() : List<MessageDTO>

    suspend fun insertMessage(message: MessageDTO)

}