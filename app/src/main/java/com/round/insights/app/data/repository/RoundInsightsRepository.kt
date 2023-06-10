package com.round.insights.app.data.repository

import com.round.insights.app.data.repository.response.RoundMatchesResponse
import com.round.insights.app.data.repository.response.RoundResponse
import com.round.insights.app.model.RoundMatchesModel

interface RoundInsightsRepository {

    suspend fun getRoundMatches(roundNumber: String): RoundMatchesModel?

    suspend fun getRoundsInformation(): List<RoundResponse>?
}
