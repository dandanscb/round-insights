package com.round.insights.app.matches.data.repository

import com.round.insights.app.matches.data.repository.response.RoundResponse
import com.round.insights.app.matches.model.RoundMatchesModel

interface RoundInsightsRepository {

    suspend fun getRoundMatches(roundNumber: String): RoundMatchesModel?

    suspend fun getRoundsInformation(): List<RoundResponse>?
}
