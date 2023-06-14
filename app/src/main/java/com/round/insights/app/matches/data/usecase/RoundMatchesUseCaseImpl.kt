package com.round.insights.app.matches.data.usecase

import com.round.insights.app.matches.data.repository.RoundMatchesRepositoryImpl
import com.round.insights.app.matches.data.repository.response.RoundResponse
import javax.inject.Inject

class RoundMatchesUseCaseImpl @Inject constructor(private val repository: RoundMatchesRepositoryImpl) :
    RoundMatchesUseCase {

    override suspend fun getRoundMatches(roundNumber: String): RoundMatchesUseCaseState.RoundMatchesInformation {
        val response = repository.getRoundMatches(roundNumber)
        response?.let {
            return RoundMatchesUseCaseState.RoundMatchesInformation.GetRoundMatches(it)
        } ?: run {
            return RoundMatchesUseCaseState.RoundMatchesInformation.Error
        }
    }

    override suspend fun getRoundNumber(): RoundMatchesUseCaseState.RoundsInformation {
        val response = repository.getRoundsInformation()
        response?.let {
            return RoundMatchesUseCaseState.RoundsInformation.GetRoundNumber(discoverRound(it))
        } ?: run {
            return RoundMatchesUseCaseState.RoundsInformation.Error
        }
    }

    private fun discoverRound(rounds: List<RoundResponse>): String {
        var round = LAST_ROUND

        rounds.map {
            if (it.status == SCHEDULED_ROUND) {
                if (it.rodada < round) {
                    round = it.rodada
                }
            }
        }

        return round.toString()
    }

    companion object {
        private const val LAST_ROUND = 38
        private const val SCHEDULED_ROUND = "agendada"
    }

}
