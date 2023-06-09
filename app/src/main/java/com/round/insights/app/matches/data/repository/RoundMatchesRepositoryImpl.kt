package com.round.insights.app.matches.data.repository

import com.round.insights.commons.network.ApiService
import com.round.insights.app.matches.data.repository.mapper.RoundMatchesMapperImpl
import com.round.insights.app.matches.data.repository.response.RoundMatchesResponse
import com.round.insights.app.matches.data.repository.response.RoundResponse
import com.round.insights.app.matches.model.RoundMatchesModel
import com.round.insights.commons.network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoundMatchesRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RoundMatchesRepository {

    override suspend fun getRoundMatches(roundNumber: String): RoundMatchesModel? {
        return try {
            val roundResponses: RoundMatchesResponse? = withContext(Dispatchers.IO) {
                apiService.getRoundMatches(
                    ApiServiceImpl.BASE_URL +
                            ApiServiceImpl.BRASILEIRAO_URL_SUFIXO + roundNumber
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
                    ApiServiceImpl.BASE_URL +
                            ApiServiceImpl.BRASILEIRAO_URL_SUFIXO
                ).execute().body()
            }
            roundResponses
        } catch (e: Exception) {
            null
        }
    }
}
