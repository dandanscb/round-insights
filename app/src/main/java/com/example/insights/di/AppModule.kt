package com.example.insights.di

import com.example.insights.data.network.ApiService
import com.example.insights.data.network.OAuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val BASE_URL = "https://api.api-futebol.com.br/v1/"
    const val BRASILEIRAO_URL_SUFIXO = "campeonatos/10/rodadas/"

    private const val TOKEN_TYPE = "Bearer"
    private const val ACCESS_TOKEN = "live_3738b9c0300714fcdc3465096819bc"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi
        .Builder()
        .run {
            add(KotlinJsonAdapterFactory())
                .build()
        }

    @Provides
    @Singleton
    fun providesApiService(moshi: Moshi, okHttpClient: OkHttpClient): ApiService =
        Retrofit
            .Builder()
            .run {
                baseUrl(BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                client(okHttpClient)
                build()
            }.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
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
}
