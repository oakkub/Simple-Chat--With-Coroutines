package com.nimbl3.data.repository

import kotlinx.coroutines.experimental.channels.Channel

interface ChatRepository {

    suspend fun sendMessage(message: String): SendMessageResult

    suspend fun messages(): Channel<MessagesResult>

}

sealed class SendMessageResult {
    data class Success(val message: String): SendMessageResult()
    data class Error(val throwable: Throwable): SendMessageResult()
}

sealed class MessagesResult {
    data class Success(val messages: kotlin.collections.List<Message>): MessagesResult()
    data class Error(val throwable: Throwable): MessagesResult()
}