package com.round.insights.app.viewmodel

import com.round.insights.app.data.repository.response.RoundMatchesResponse

sealed class RoundInsightsViewModelState {
    data class GetRoundNumber(val roundNumber: String) : RoundInsightsViewModelState()
    data class GetRoundMatches(val matches: RoundMatchesResponse) : RoundInsightsViewModelState()
    object GenericError : RoundInsightsViewModelState()
}
