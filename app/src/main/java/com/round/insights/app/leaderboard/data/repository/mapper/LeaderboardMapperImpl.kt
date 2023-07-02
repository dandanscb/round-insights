package com.round.insights.app.leaderboard.data.repository.mapper

import com.round.insights.R
import com.round.insights.app.leaderboard.data.repository.response.LeaderboardResponse
import com.round.insights.app.leaderboard.model.LeaderboardModel
import com.round.insights.app.leaderboard.model.PositionVariationModel
import com.round.insights.app.matches.data.repository.response.TeamResponse
import com.round.insights.app.matches.model.TeamModel

object LeaderboardMapperImpl {

    private const val FIRST_POSITION = 1
    private const val ZERO = 0


    private const val VICTORY = "v"
    private const val DRAW = "e"
    private const val LOSE = "d"

    fun convertLeaderboardResponseToModel(response: List<LeaderboardResponse>): List<LeaderboardModel> {
        val model = mutableListOf<LeaderboardModel>()

        response.map {
            model.add(
                LeaderboardModel(
                    position = it.posicao ?: FIRST_POSITION,
                    points = it.pontos ?: ZERO,
                    team = convertTeamResponseToModel(it.time),
                    matches = it.jogos ?: ZERO,
                    wonMatches = it.vitorias ?: ZERO,
                    drawMatches = it.empates ?: ZERO,
                    lostMatches = it.derrotas ?: ZERO,
                    goalsScored = it.gols_pro ?: ZERO,
                    goalsTaken = it.gols_contra ?: ZERO,
                    goalDifference = it.saldo_gols ?: ZERO,
                    accuracy = "${it.aproveitamento}%",
                    positionVariation = positionVariation(it.variacao_posicao ?: ZERO),
                    lastMatches = getLastMatchesColors(it.ultimos_jogos)
                )
            )
        }

        return model
    }

    private fun convertTeamResponseToModel(teamResponse: TeamResponse) = TeamModel(
        teamId = teamResponse.time_id,
        teamName = teamResponse.nome_popular,
        teamAcronym = teamResponse.sigla,
        teamCrest = teamResponse.escudo
    )

    private fun positionVariation(variation: Int): PositionVariationModel {
        val icon = R.drawable.ic_leaderboard
        val color = R.color.round_insights_gray

        if (variation == ZERO) {
            // TODO
        } else if (variation > ZERO) {
            // TODO
        } else {
            // TODO
        }

        return PositionVariationModel(
            icon, color, variation
        )
    }

    private fun getLastMatchesColors(lastMatches: List<String>?): List<Int> {
        val colors = mutableListOf<Int>()

        lastMatches?.map {
            colors.add(
                when (it) {
                    VICTORY -> {
                        R.color.round_insights_green
                    }

                    LOSE -> {
                        R.color.round_insights_red
                    }

                    else -> {
                        R.color.round_insights_gray
                    }
                }
            )
        }

        return colors
    }
}
