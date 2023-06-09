package com.example.insights.data.usecase

import com.example.insights.data.RoundMatchesResponse

sealed class RoundInsightsUseCaseState {

    sealed class RoundsInformation {
        data class GetRoundNumber(val round: String) : RoundsInformation()
        object Error : RoundsInformation()
    }

    sealed class RoundMatchesInformation {
        data class GetRoundMatches(val matches: RoundMatchesResponse) : RoundMatchesInformation()
        object Error : RoundMatchesInformation()
    }
}
