package com.round.insights.app.leaderboard.data.repository.response

import com.round.insights.app.matches.data.repository.response.TeamResponse

data class LeaderboardResponse(
    val posicao: Int?,
    val pontos: Int?,
    val time: TeamResponse,
    val jogos: Int?,
    val vitorias: Int?,
    val empates: Int?,
    val derrotas: Int?,
    val gols_pro: Int?,
    val gols_contra: Int?,
    val saldo_gols: Int?,
    val aproveitamento: Int?,
    val variacao_posicao: Int?,
    val ultimos_jogos: List<String>?
)
