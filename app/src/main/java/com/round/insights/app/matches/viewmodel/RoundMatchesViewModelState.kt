package com.round.insights.app.matches.viewmodel

import com.round.insights.app.matches.model.RoundMatchesModel

sealed class RoundMatchesViewModelState {
    data class GetRoundNumber(val roundNumber: String) : RoundMatchesViewModelState()
    data class GetRoundMatches(val matches: RoundMatchesModel) : RoundMatchesViewModelState()
    object GenericError : RoundMatchesViewModelState()
}
