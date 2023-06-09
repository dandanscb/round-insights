package com.round.insights.commons.network

import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor constructor(
    private val accessToken:String,
    private val tokenType:String
) :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().header("Authorization","$tokenType $accessToken")
            .build()
        return chain.proceed(request)
    }
}
