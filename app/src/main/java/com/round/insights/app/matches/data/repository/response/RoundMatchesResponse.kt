package com.round.insights.app.matches.data.repository.response

data class RoundMatchesResponse(
    val nome: String,
    val rodada: Int,
    val status: String,
    val partidas: List<MatchesResponse>
)
