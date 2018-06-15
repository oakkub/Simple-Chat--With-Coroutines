package com.nimbl3.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.nimbl3.data.repository.FirebaseEmailLoginRepository
import com.nimbl3.data.repository.LoginRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository = FirebaseEmailLoginRepository(firebaseAuth)

}