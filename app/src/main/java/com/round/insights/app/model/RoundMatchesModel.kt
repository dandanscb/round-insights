package com.round.insights.app.model

data class RoundMatchesModel(
    val roundName: String,
    val roundNumber: Int,
    val roundStatus: String,
    val matches: List<MatchesModel>
)
