package com.easymove.easymove.auth.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easymove.easymove.R
import org.koin.android.viewmodel.ext.android.getViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = getViewModel()

        viewModel.sayHello()
    }
}