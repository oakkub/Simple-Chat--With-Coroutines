package com.nimbl3.di.modules

import com.nimbl3.MainActivity
import com.nimbl3.ui.chat.ChatActivity
import com.nimbl3.ui.chat.di.ChatActivityModule
import com.nimbl3.ui.main.di.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ChatActivityModule::class])
    abstract fun bindChatActivity(): ChatActivity

}
