package com.doachgosum.marketsampleapp.data.remote

import android.util.Log
import com.doachgosum.marketsampleapp.constant.LogTag
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        Log.d(
            LogTag.TAG_NETWORK,
            "[RESPONSE] URL: ${request.url().url()}" +
                    "\nHEADERS: ${request.headers()}" +
                    "\nBODY: ${request.body().toString()}"
        )

        val response = chain.proceed(request)
        Log.d(
            LogTag.TAG_NETWORK,
            "[RESPONSE] URL: ${response.request().url()}" +
                    "\nHEADERS: ${response.headers()}" +
                    "\nBODY: ${response.peekBody(Long.MAX_VALUE).string()}"
        )
        return response
    }
}