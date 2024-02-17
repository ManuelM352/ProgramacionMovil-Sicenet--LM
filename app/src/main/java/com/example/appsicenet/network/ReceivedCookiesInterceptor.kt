package com.example.appsicenet.network

import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.Response
import java.io.IOException


class ReceivedCookiesInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet("PREF_COOKIES", HashSet()) ?: HashSet()

            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }

            PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putStringSet("PREF_COOKIES", cookies)
                .apply()
        }

        return originalResponse
    }
}
