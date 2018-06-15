package com.nimbl3.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nimbl3.data.repository.ChatRepository
import com.nimbl3.data.repository.ChatRepositoryImpl
import com.nimbl3.data.repository.FirebaseEmailLoginRepository
import com.nimbl3.data.repository.LoginRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository = FirebaseEmailLoginRepository(firebaseAuth)

    @Provides
    fun provideChatRepository(
        firebaseDatabase: FirebaseDatabase,
        firebaseAuth: FirebaseAuth
    ): ChatRepository = ChatRepositoryImpl(firebaseDatabase, firebaseAuth)

}