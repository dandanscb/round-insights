package com.round.insights.app.matches.data.usecase

interface RoundInsightsUseCase {

    suspend fun getRoundMatches(roundNumber: String): RoundInsightsUseCaseState.RoundMatchesInformation

    suspend fun getRoundNumber(): RoundInsightsUseCaseState.RoundsInformation
}
