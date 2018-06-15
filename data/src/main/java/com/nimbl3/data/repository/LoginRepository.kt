package com.nimbl3.data.repository

interface LoginRepository {

    suspend fun login(username: String, password: String): LoginResult

}

sealed class LoginResult {
    data class Success(val id: String, val username: String): LoginResult()
    object YourPasswordSucks: LoginResult()
    object YourEmailSucks: LoginResult()
    object WrongPasswordDuh: LoginResult()
    data class Error(val throwable: Throwable): LoginResult()
}