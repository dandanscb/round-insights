package com.round.insights.app.leaderboard.data.usecase

import com.round.insights.app.leaderboard.model.LeaderboardModel

sealed class LeaderboardUseCaseState {

    data class GetLeaderBoard(val teams: List<LeaderboardModel>) : LeaderboardUseCaseState()
    object Error : LeaderboardUseCaseState()
}