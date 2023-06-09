package com.round.insights.app.viewmodel

import com.round.insights.app.data.repository.response.RoundMatchesResponse

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
