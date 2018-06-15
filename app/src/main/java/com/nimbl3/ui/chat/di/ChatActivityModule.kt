package com.nimbl3.ui.chat.di

import androidx.lifecycle.ViewModel
import com.nimbl3.di.ViewModelKey
import com.nimbl3.ui.chat.ChatViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChatActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(viewMode: ChatViewModel): ViewModel

}