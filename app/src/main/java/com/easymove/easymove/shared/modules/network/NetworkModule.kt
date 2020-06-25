package com.easymove.easymove.shared.modules.network

import com.easymove.easymove.BuildConfig
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        AuthTokenInterceptor(get())
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthTokenInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    single { ConnectivityObserver(get()) }
}