package com.example.todomateclone

import android.app.Application
import com.example.todomateclone.di.appModule
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}