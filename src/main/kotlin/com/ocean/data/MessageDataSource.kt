package com.ocean.data

import com.ocean.data.dto.MessageDTO

interface MessageDataSource {

    suspend fun getAllMessages() : List<MessageDTO>

    suspend fun insertMessage(message: MessageDTO)

}