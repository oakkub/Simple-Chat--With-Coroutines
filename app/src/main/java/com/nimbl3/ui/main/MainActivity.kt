package com.nimbl3

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nimbl3.extension.toastLong
import com.nimbl3.ui.base.BaseActivity
import com.nimbl3.ui.chat.ChatActivity
import com.nimbl3.ui.main.MainViewModel
import com.nimbl3.ui.main.MainViewModelState
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton.setOnClickListener {
            viewModel.login(usernameTextInputEditText.text.toString(), passwordTextInputEditText.text.toString())
        }

        viewModel.state.observe(this, Observer {
            when(it) {
                is MainViewModelState.LoggingIn -> toastLong("Logging in")
                is MainViewModelState.LoginFailed -> toastLong(it.message)
                is MainViewModelState.LoginError -> toastLong(it.throwable.toString())
                is MainViewModelState.LoginSuccess -> ChatActivity.show(this)
            }
        })
    }


}