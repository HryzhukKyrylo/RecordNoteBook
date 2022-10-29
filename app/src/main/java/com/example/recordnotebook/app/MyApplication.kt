package com.example.recordnotebook.app

import android.app.Application
import com.example.data.di.userDatabaseModule
import com.example.recordnotebook.di.appModule
import com.example.recordnotebook.di.dataModule
import com.example.recordnotebook.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val modules = listOf(appModule, dataModule, domainModule, userDatabaseModule)
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(androidContext = this@MyApplication)
            modules(modules)
        }
    }
}