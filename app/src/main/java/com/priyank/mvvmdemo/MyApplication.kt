package com.priyank.mvvmdemo

import android.app.Application
import com.priyank.mvvmdemo.di.networkModule
import com.priyank.mvvmdemo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
    }

    private fun init() {
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    viewModelModule
                )
            )
        }
    }

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance(): MyApplication = instance
    }
}
