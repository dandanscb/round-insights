package com.round.insights.app.matches.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.round.insights.app.matches.data.usecase.RoundInsightsUseCaseImpl
import com.round.insights.app.matches.data.usecase.RoundInsightsUseCaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoundInsightsViewModel
@Inject
constructor(
    private val useCase: RoundInsightsUseCaseImpl
) : ViewModel() {

    private val roundNumberStateFlow: MutableStateFlow<RoundInsightsViewModelState> =
        MutableStateFlow(RoundInsightsViewModelState.GenericError)
    val roundNumberViewState: StateFlow<RoundInsightsViewModelState> =
        roundNumberStateFlow

    fun getRoundMatches(roundNumber: String) {
        viewModelScope.launch {
            val result = useCase.getRoundMatches(roundNumber)
            handleRoundMatches(result)
        }
    }

    private fun handleRoundMatches(result: RoundInsightsUseCaseState.RoundMatchesInformation) {
        when (result) {
            is RoundInsightsUseCaseState.RoundMatchesInformation.GetRoundMatches -> {
                roundNumberStateFlow.value =
                    RoundInsightsViewModelState.GetRoundMatches(result.matches)
            }

            is RoundInsightsUseCaseState.RoundMatchesInformation.Error -> {
                roundNumberStateFlow.value = RoundInsightsViewModelState.GenericError
            }
        }
    }

    fun getRoundNumber() {
        viewModelScope.launch {
            val result = useCase.getRoundNumber()
            handleRoundNumber(result)
        }
    }

    private fun handleRoundNumber(result: RoundInsightsUseCaseState.RoundsInformation) {
        when (result) {
            is RoundInsightsUseCaseState.RoundsInformation.GetRoundNumber -> {
                roundNumberStateFlow.value =
                    RoundInsightsViewModelState.GetRoundNumber(result.round)
            }

            is RoundInsightsUseCaseState.RoundsInformation.Error -> {
                roundNumberStateFlow.value = RoundInsightsViewModelState.GenericError
            }
        }
    }
}
