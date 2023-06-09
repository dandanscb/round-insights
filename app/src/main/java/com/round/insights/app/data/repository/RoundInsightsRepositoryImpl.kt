package com.round.insights.app.data.repository

import com.round.insights.app.data.repository.response.RoundMatchesResponse
import com.round.insights.app.data.repository.response.RoundResponse
import com.round.insights.commons.network.ApiService
import com.round.insights.AppModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoundInsightsRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RoundInsightsRepository {

    override suspend fun getRoundMatches(roundNumber: String): RoundMatchesResponse? {
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

    override suspend fun getRoundsInformation(): List<RoundResponse>? {
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
