package com.priyank.mvvmdemo

import android.app.Application
import com.priyank.mvvmdemo.di.networkModule
import com.priyank.mvvmdemo.di.viewModelModule
import org.koin.core.context.startKoin


open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        startKoin {
            modules(
                listOf(
                    networkModule,
                    viewModelModule
                )
            )
        }
    }
}
