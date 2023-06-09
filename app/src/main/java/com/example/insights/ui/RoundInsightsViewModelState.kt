package com.example.insights.ui

import com.example.insights.data.RoundMatchesResponse

sealed class RoundInsightsViewModelState {

    sealed class RoundNumber {
        data class GetRoundNumber(val roundNumber: String) : RoundNumber()
        object Error : RoundNumber()
        data class GetRoundMatches(val matches: RoundMatchesResponse) : RoundNumber()
        object ABC : RoundNumber()
    }

    sealed class RoundMatches {
        data class GetRoundMatches(val matches: RoundMatchesResponse) : RoundMatches()
        object Error : RoundMatches()
    }
}
