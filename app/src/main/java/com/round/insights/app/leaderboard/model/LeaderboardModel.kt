package com.round.insights.app.leaderboard.model

import com.round.insights.app.matches.model.TeamModel

data class LeaderboardModel(
    val position: Int,
    val points: Int,
    val team: TeamModel,
    val matches: Int,
    val wonMatches: Int,
    val drawMatches: Int,
    val lostMatches: Int,
    val goalsScored: Int,
    val goalsTaken: Int,
    val goalDifference: Int,
    val accuracy: String,
    val positionVariation: PositionVariationModel,
    val lastMatches: List<Int>
)
