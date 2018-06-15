package com.nimbl3.ui.main

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.nimbl3.data.repository.LoginRepository
import com.nimbl3.data.repository.LoginResult
import com.nimbl3.lib.CoroutinesContextProvider
import com.nimbl3.ui.base.BaseViewModel
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(val loginRepository: LoginRepository,
                                        val coroutinesProvider: CoroutinesContextProvider) : BaseViewModel() {

    val state: MutableLiveData<MainViewModelState> = MutableLiveData()

    fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            state.value = MainViewModelState.LoginFailed("Username or Password is empty")
            return
        }

        launch(coroutinesProvider.ui) {
            state.value = MainViewModelState.LoggingIn

            val result = withContext(coroutinesProvider.bg) { loginRepository.login(username, password) }

            state.value = when (result) {
                is LoginResult.Success -> MainViewModelState.LoginSuccess(result.id, result.username)
                is LoginResult.WrongPasswordDuh -> MainViewModelState.LoginFailed("Wrong password duh")
                is LoginResult.YourEmailSucks -> MainViewModelState.LoginFailed("Your email sucks")
                is LoginResult.YourPasswordSucks -> MainViewModelState.LoginFailed("Your password sucks, use something else")
                is LoginResult.Error -> MainViewModelState.LoginError(result.throwable)
            }
        }
    }

}

sealed class MainViewModelState {
    object LoggingIn : MainViewModelState()
    data class LoginSuccess(val id: String, val email: String) : MainViewModelState()
    data class LoginFailed(val message: String) : MainViewModelState()
    data class LoginError(val throwable: Throwable) : MainViewModelState()
}
