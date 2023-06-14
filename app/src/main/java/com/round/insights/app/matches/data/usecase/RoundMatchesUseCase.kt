package com.round.insights.app.matches.data.usecase

interface RoundMatchesUseCase {

    suspend fun getRoundMatches(roundNumber: String): RoundMatchesUseCaseState.RoundMatchesInformation

    suspend fun getRoundNumber(): RoundMatchesUseCaseState.RoundsInformation
}
