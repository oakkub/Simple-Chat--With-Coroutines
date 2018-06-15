package com.nimbl3.di.modules

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}