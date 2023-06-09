package com.example.insights.data

data class MatchesResponse(
    val time_mandante: TeamResponse,
    val time_visitante: TeamResponse,
    val data_realizacao: String,
    val hora_realizacao: String,
    val estadio: StadiumResponse
)