package com.easymove.easymove.auth

import android.os.Bundle
import com.easymove.easymove.R
import com.easymove.easymove.shared.DataBindingActivity

class AuthActivity : DataBindingActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}