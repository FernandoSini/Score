package com.flemis.score

import android.app.Application
import android.content.Context
import com.flemis.score.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent

class MyApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin {
            androidContext(this@MyApplication)
        }
    }

    companion object {
        private var instance: MyApplication = MyApplication()

        fun applicationContext(): Context {
            return instance.applicationContext
        }


    }
}