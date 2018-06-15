package com.nimbl3.data.repository

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.util.concurrent.ExecutionException
import javax.inject.Inject
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine

class FirebaseEmailLoginRepository @Inject constructor(val firebaseAuth: FirebaseAuth) : LoginRepository {

    override suspend fun login(username: String, password: String): LoginResult = suspendCoroutine { continuation ->
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            continuation.resume(LoginResult.Success(currentUser.uid, currentUser.email ?: ""))
            return@suspendCoroutine
        }

        try {
            val signedInResult = Tasks.await(firebaseAuth.createUserWithEmailAndPassword(username, password))
            resumeWithSuccessResult(continuation, signedInResult)
            return@suspendCoroutine
        } catch (e: ExecutionException) {
            when (e.cause) {
                is FirebaseAuthInvalidUserException -> {
                    // ignore
                }
                is FirebaseAuthWeakPasswordException -> {
                    continuation.resume(LoginResult.YourPasswordSucks)
                    return@suspendCoroutine
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    continuation.resume(LoginResult.YourEmailSucks)
                    return@suspendCoroutine
                }
                else -> {
                    continuation.resume(LoginResult.Error(e))
                    return@suspendCoroutine
                }
            }
        }

        try {
            val userCreatedResult = Tasks.await(firebaseAuth.createUserWithEmailAndPassword(username, password))
            resumeWithSuccessResult(continuation, userCreatedResult)
        } catch (e: ExecutionException) {
            when (e.cause) {
                is FirebaseAuthWeakPasswordException -> continuation.resume(LoginResult.YourPasswordSucks)
                is FirebaseAuthInvalidCredentialsException -> continuation.resume(LoginResult.YourEmailSucks)
                else -> continuation.resume(LoginResult.Error(e))
            }
        }
    }

    private fun resumeWithSuccessResult(continuation: Continuation<LoginResult>, authResult: AuthResult) {
        val user = authResult.user
        continuation.resume(LoginResult.Success(user.uid, user.email ?: ""))
    }

}