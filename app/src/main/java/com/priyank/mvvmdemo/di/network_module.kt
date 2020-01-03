package com.priyank.mvvmdemo.di

import com.priyank.mvvmdemo.BuildConfig
import com.priyank.mvvmdemo.domain.network.ApiServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit


val networkModule = module {

    single { createOkHttpClient() }

    single { createWebService(get()) }

}

fun createOkHttpClient(): OkHttpClient {

    val cookieManager = CookieManager()
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return OkHttpClient.Builder()
        .connectTimeout(90L, TimeUnit.SECONDS)
        .readTimeout(90L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .cookieJar(MyCookieJar())
        .build()
}

fun createWebService(okHttpClient: OkHttpClient): ApiServices {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiServices::class.java)
}

