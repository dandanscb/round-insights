package com.example.insights.data.repository

import com.example.insights.data.RoundMatchesResponse
import com.example.insights.data.RoundResponse
import com.example.insights.data.network.ApiService
import com.example.insights.di.AppModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getRoundMatches(roundNumber: String) : RoundMatchesResponse? {
        return try {
            val roundResponses: RoundMatchesResponse? = withContext(Dispatchers.IO) {
                apiService.getRoundMatches(
                    AppModule.BASE_URL +
                            AppModule.BRASILEIRAO_URL_SUFIXO + roundNumber
                ).execute().body()
            }
            roundResponses
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getRoundsInformation() : List<RoundResponse>? {
        return try {
            val roundResponses: List<RoundResponse>? = withContext(Dispatchers.IO) {
                apiService.getRoundsInformation(
                    AppModule.BASE_URL +
                            AppModule.BRASILEIRAO_URL_SUFIXO
                ).execute().body()
            }
            roundResponses
        } catch (e: Exception) {
            null
        }
    }
}
