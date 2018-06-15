package com.nimbl3.di.modules

import com.nimbl3.lib.CoroutinesContextProvider
import com.nimbl3.lib.CoroutinesContextProviderImpl
import dagger.Module
import dagger.Provides

@Module
class CoroutinesModule {

    @Provides
    fun provideCoroutinesContexts(): CoroutinesContextProvider = CoroutinesContextProviderImpl()

}