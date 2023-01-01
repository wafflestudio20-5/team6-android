package com.example.todomateclone.di

import android.content.Context
import com.example.todomateclone.network.RestService
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.util.LocalDateTimeConverter
import com.example.todomateclone.util.Toaster
import com.example.todomateclone.viewmodel.UserViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single<Retrofit> {
        val context: Context = get()
        val sharedPreference =
            context.getSharedPreferences(AuthStorage.SharedPreferenceName, Context.MODE_PRIVATE)
        Retrofit.Builder()
            .baseUrl("https://account.readthedocs.io/en/latest/api_endpoints.html/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor {
                        val newRequest = it.request().newBuilder()
                            .addHeader(
                                "Authorization",
                                "Bearer " + sharedPreference.getString(
                                    AuthStorage.AccessTokenKey,
                                    ""
                                )
                            )
                            .build()
                        it.proceed(newRequest)
                    }
                    .build()
            )
            .build()
    }

    single<RestService> {
        get<Retrofit>().create(RestService::class.java)
    }

    single { AuthStorage(get()) }

    single { Toaster(get(), get()) }

    single<Moshi> {
        Moshi.Builder()
            .add(LocalDateTimeConverter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    viewModel { UserViewModel(get(), get(), get())}
}