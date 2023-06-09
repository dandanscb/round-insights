package com.example.insights.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insights.data.usecase.RoundInsightsUseCase
import com.example.insights.data.usecase.RoundInsightsUseCaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val useCase: RoundInsightsUseCase
) : ViewModel() {

    private val roundNumberStateFlow: MutableStateFlow<RoundInsightsViewModelState.RoundNumber> =
        MutableStateFlow(RoundInsightsViewModelState.RoundNumber.Error)
    val roundNumberViewState: StateFlow<RoundInsightsViewModelState.RoundNumber> =
        roundNumberStateFlow

    private val roundMatchesStateFlow: MutableStateFlow<RoundInsightsViewModelState.RoundMatches> =
        MutableStateFlow(RoundInsightsViewModelState.RoundMatches.Error)
    val roundMatchesViewStateFlow: StateFlow<RoundInsightsViewModelState.RoundMatches> =
        roundMatchesStateFlow

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
                    RoundInsightsViewModelState.RoundNumber.GetRoundMatches(result.matches)
            }

            is RoundInsightsUseCaseState.RoundMatchesInformation.Error -> {
                roundNumberStateFlow.value = RoundInsightsViewModelState.RoundNumber.Error
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
                    RoundInsightsViewModelState.RoundNumber.GetRoundNumber(result.round)
            }

            is RoundInsightsUseCaseState.RoundsInformation.Error -> {
                roundNumberStateFlow.value = RoundInsightsViewModelState.RoundNumber.Error
            }
        }
    }
}
