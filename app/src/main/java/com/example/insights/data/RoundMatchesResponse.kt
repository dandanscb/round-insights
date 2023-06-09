package com.example.insights.data

data class RoundMatchesResponse(
    val nome: String,
    val rodada: Int,
    val status: String,
    val partidas: List<MatchesResponse>
)
