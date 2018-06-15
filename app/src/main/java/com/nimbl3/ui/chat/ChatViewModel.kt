package com.nimbl3.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nimbl3.data.repository.ChatRepository
import com.nimbl3.data.repository.Message
import com.nimbl3.data.repository.MessagesResult
import com.nimbl3.lib.CoroutinesContextProvider
import com.nimbl3.ui.base.BaseViewModel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(val chatRepository: ChatRepository,
                                        val coroutinesContextProvider: CoroutinesContextProvider) : BaseViewModel() {

    val messages: MutableLiveData<List<Message>> = MutableLiveData()

    init {
        launch(coroutinesContextProvider.ui) {
            chatRepository.messages().consumeEach {
                if (it !is MessagesResult.Success) {
                    return@consumeEach
                }
                messages.value = it.messages
            }
        }
    }

    fun send(message: String) {
        if (message.isBlank()) return

        launch(coroutinesContextProvider.bg) {
            chatRepository.sendMessage(message)
        }
    }

}