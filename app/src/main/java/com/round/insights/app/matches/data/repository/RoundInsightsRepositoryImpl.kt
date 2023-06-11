package com.round.insights.app.matches.data.repository

import com.round.insights.commons.network.ApiService
import com.round.insights.RoundInsightsAppModule
import com.round.insights.app.matches.data.repository.mapper.RoundMatchesMapperImpl
import com.round.insights.app.matches.data.repository.response.RoundMatchesResponse
import com.round.insights.app.matches.data.repository.response.RoundResponse
import com.round.insights.app.matches.model.RoundMatchesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoundInsightsRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RoundInsightsRepository {

    override suspend fun getRoundMatches(roundNumber: String): RoundMatchesModel? {
        return try {
            val roundResponses: RoundMatchesResponse? = withContext(Dispatchers.IO) {
                apiService.getRoundMatches(
                    RoundInsightsAppModule.BASE_URL +
                            RoundInsightsAppModule.BRASILEIRAO_URL_SUFIXO + roundNumber
                ).execute().body()
            }
            roundResponses?.let {
                RoundMatchesMapperImpl.convertRoundMatchesResponseToModel(roundResponses)
            } ?: run {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getRoundsInformation(): List<RoundResponse>? {
        return try {
            val roundResponses: List<RoundResponse>? = withContext(Dispatchers.IO) {
                apiService.getRoundsInformation(
                    RoundInsightsAppModule.BASE_URL +
                            RoundInsightsAppModule.BRASILEIRAO_URL_SUFIXO
                ).execute().body()
            }
            roundResponses
        } catch (e: Exception) {
            null
        }
    }
}
