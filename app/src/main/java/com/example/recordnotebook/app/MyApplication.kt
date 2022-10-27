package com.example.recordnotebook.app

import android.app.Application
import com.example.recordnotebook.di.appModule
import com.example.recordnotebook.di.dataModule
import com.example.recordnotebook.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val modules = listOf(appModule, dataModule, domainModule)
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(androidContext = this@MyApplication)
            koin.loadModules(modules = modules)
        }
    }
}