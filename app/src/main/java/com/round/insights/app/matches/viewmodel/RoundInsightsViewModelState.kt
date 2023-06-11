package com.round.insights.app.matches.viewmodel

import com.round.insights.app.matches.model.RoundMatchesModel

sealed class RoundInsightsViewModelState {
    data class GetRoundNumber(val roundNumber: String) : RoundInsightsViewModelState()
    data class GetRoundMatches(val matches: RoundMatchesModel) : RoundInsightsViewModelState()
    object GenericError : RoundInsightsViewModelState()
}
