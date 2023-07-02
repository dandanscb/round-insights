package com.round.insights.app.leaderboard.data.usecase

import com.round.insights.app.leaderboard.data.repository.LeaderboardRepositoryImpl
import javax.inject.Inject

class LeaderboardUseCaseImpl @Inject constructor(private val repository: LeaderboardRepositoryImpl) :
    LeaderboardUseCase {


}
