package com.ocean.data

import com.ocean.data.dto.MessageDTO

class MessageDataSourceImpl (

) : MessageDataSource {

    //Получить все сообщения
    //private val messages : List<MessageDTO> = List

    override suspend fun getAllMessages(): List<MessageDTO> {
        TODO("Сообщения, отправители и получатели которых совпадают с полученными, сортировать их по дате и отправить")
    }

    override suspend fun insertMessage(message: MessageDTO) {
        TODO("Вставка в таблицу сообщений")
    }
}