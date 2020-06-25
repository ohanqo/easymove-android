package com.easymove.easymove.shared.modules.network

import com.easymove.easymove.shared.utils.PrefsUtils
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(private val prefsUtils: PrefsUtils) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${prefsUtils.authToken}")
                .build()
        )
    }
}