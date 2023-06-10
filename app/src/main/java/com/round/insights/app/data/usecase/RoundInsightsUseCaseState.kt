package com.round.insights.app.data.usecase

import com.round.insights.app.model.RoundMatchesModel

sealed class RoundInsightsUseCaseState {

    sealed class RoundsInformation {
        data class GetRoundNumber(val round: String) : RoundsInformation()
        object Error : RoundsInformation()
    }

    sealed class RoundMatchesInformation {
        data class GetRoundMatches(val matches: RoundMatchesModel) : RoundMatchesInformation()
        object Error : RoundMatchesInformation()
    }
}
