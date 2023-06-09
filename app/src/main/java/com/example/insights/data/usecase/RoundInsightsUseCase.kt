package com.example.insights.data.usecase

import com.example.insights.data.RoundResponse
import com.example.insights.data.repository.MainRepository
import javax.inject.Inject

class RoundInsightsUseCase @Inject constructor(private val repository: MainRepository) {

    suspend fun getRoundMatches(roundNumber: String): RoundInsightsUseCaseState.RoundMatchesInformation {
        val response = repository.getRoundMatches(roundNumber)
        response?.let {
            return RoundInsightsUseCaseState.RoundMatchesInformation.GetRoundMatches(it)
        } ?: run {
            return RoundInsightsUseCaseState.RoundMatchesInformation.Error
        }
    }

    suspend fun getRoundNumber(): RoundInsightsUseCaseState.RoundsInformation {
        val response = repository.getRoundsInformation()
        response?.let {
            return RoundInsightsUseCaseState.RoundsInformation.GetRoundNumber(discoverRound(it))
        } ?: run {
            return RoundInsightsUseCaseState.RoundsInformation.Error
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