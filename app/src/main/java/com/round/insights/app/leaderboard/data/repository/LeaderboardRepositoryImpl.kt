package com.round.insights.app.leaderboard.data.repository

import com.round.insights.commons.network.ApiService
import javax.inject.Inject

class LeaderboardRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    LeaderboardRepository {
}
