package com.round.insights.app.matches.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.round.insights.app.matches.data.repository.RoundMatchesRepositoryImpl
import com.round.insights.app.matches.data.usecase.RoundMatchesUseCaseImpl
import com.round.insights.commons.network.ApiServiceImpl

class RoundMatchesViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoundMatchesViewModel(
            useCase = RoundMatchesUseCaseImpl(
                repository = RoundMatchesRepositoryImpl(
                    apiService = ApiServiceImpl()
                )
            )
        ) as T
    }
}
