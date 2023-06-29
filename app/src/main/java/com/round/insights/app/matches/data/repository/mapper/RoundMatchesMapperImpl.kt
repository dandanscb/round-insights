package com.round.insights.app.matches.data.repository.mapper

import com.round.insights.app.matches.data.repository.response.MatchesResponse
import com.round.insights.app.matches.data.repository.response.RoundMatchesResponse
import com.round.insights.app.matches.data.repository.response.StadiumResponse
import com.round.insights.app.matches.data.repository.response.TeamResponse
import com.round.insights.app.matches.model.MatchesModel
import com.round.insights.app.matches.model.RoundMatchesModel
import com.round.insights.app.matches.model.StadiumModel
import com.round.insights.app.matches.model.TeamModel

object RoundMatchesMapperImpl {

    fun convertRoundMatchesResponseToModel(response: RoundMatchesResponse) = RoundMatchesModel(
        roundName = response.nome,
        roundNumber = response.rodada,
        roundStatus = response.status,
        matches = convertMatchesResponseToModel(response.partidas)
    )

    private fun convertMatchesResponseToModel(matches: List<MatchesResponse>): List<MatchesModel> {
        val list = mutableListOf<MatchesModel>()

        matches.map { response ->
            list.add(
                MatchesModel(
                    homeTeam = convertTeamResponseToModel(response.time_mandante),
                    outTeam = convertTeamResponseToModel(response.time_visitante),
                    matchDate = response.data_realizacao ?: "",
                    matchHour = response.hora_realizacao ?: "",
                    stadium = convertStadiumResponseToModel(response.estadio)
                )
            )
        }

        return list
    }

    private fun convertTeamResponseToModel(teamResponse: TeamResponse) = TeamModel(
        teamId = teamResponse.time_id,
        teamName = teamResponse.nome_popular,
        teamAcronym = teamResponse.sigla,
        teamCrest = teamResponse.escudo
    )

    private fun convertStadiumResponseToModel(stadiumResponse: StadiumResponse?) = StadiumModel(
        stadiumId = stadiumResponse?.estadio_id,
        stadiumName = stadiumResponse?.nome_popular ?: ""
    )
}
