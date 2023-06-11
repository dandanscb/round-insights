package com.round.insights.app.matches.model

data class MatchesModel(
    val homeTeam: TeamModel,
    val outTeam: TeamModel,
    val matchDate: String,
    val matchHour: String,
    val stadium: StadiumModel
)