package com.round.insights.commons.network

import com.round.insights.app.matches.data.repository.response.RoundMatchesResponse
import com.round.insights.app.matches.data.repository.response.RoundResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServiceImpl : ApiService {

    override fun getRoundMatches(url: String?): Call<RoundMatchesResponse> =
        providesApiService(providesMoshi(), providesOkHttpClient()).getRoundMatches(url)

    override fun getRoundsInformation(url: String?): Call<List<RoundResponse>> =
        providesApiService(providesMoshi(), providesOkHttpClient()).getRoundsInformation(url)

    private fun providesMoshi(): Moshi = Moshi
        .Builder()
        .run {
            add(KotlinJsonAdapterFactory())
                .build()
        }

    private fun providesApiService(moshi: Moshi, okHttpClient: OkHttpClient): ApiService =
        Retrofit
            .Builder()
            .run {
                baseUrl(BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                client(okHttpClient)
                build()
            }.create(ApiService::class.java)

    private fun providesOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(
            OAuthInterceptor(
                ACCESS_TOKEN,
                TOKEN_TYPE
            )
        )
            .addInterceptor(interceptor)
            .build()
    }

    companion object {
        const val BASE_URL = "https://api.api-futebol.com.br/v1/"
        const val BRASILEIRAO_URL_SUFIXO = "campeonatos/10/rodadas/"

        private const val TOKEN_TYPE = "Bearer"
        private const val ACCESS_TOKEN = "live_3738b9c0300714fcdc3465096819bc"
        // live_3738b9c0300714fcdc3465096819bc
        // test_c40cca33362d4684448435a7f786c3
    }
}