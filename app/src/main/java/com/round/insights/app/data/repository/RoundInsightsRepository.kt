package com.round.insights.app.data.repository

import com.round.insights.app.data.repository.response.RoundMatchesResponse
import com.round.insights.app.data.repository.response.RoundResponse

interface RoundInsightsRepository {

    suspend fun getRoundMatches(roundNumber: String): RoundMatchesResponse?

    suspend fun getRoundsInformation(): List<RoundResponse>?
}
