package com.priyank.mvvmdemo.di

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

open class MyCookieJar : CookieJar {

    private var cookies: List<Cookie>? = arrayListOf()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return if (cookies != null) cookies!! else ArrayList()
    }
}