package com.round.insights.app.matches.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.round.insights.app.matches.data.usecase.RoundMatchesUseCaseImpl
import com.round.insights.app.matches.data.usecase.RoundMatchesUseCaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoundMatchesViewModel
@Inject
constructor(
    private val useCase: RoundMatchesUseCaseImpl
) : ViewModel() {

    private val roundNumberStateFlow: MutableStateFlow<RoundMatchesViewModelState> =
        MutableStateFlow(RoundMatchesViewModelState.GenericError)
    val roundNumberViewState: StateFlow<RoundMatchesViewModelState> =
        roundNumberStateFlow

    fun getRoundMatches(roundNumber: String) {
        viewModelScope.launch {
            val result = useCase.getRoundMatches(roundNumber)
            handleRoundMatches(result)
        }
    }

    private fun handleRoundMatches(result: RoundMatchesUseCaseState.RoundMatchesInformation) {
        when (result) {
            is RoundMatchesUseCaseState.RoundMatchesInformation.GetRoundMatches -> {
                roundNumberStateFlow.value =
                    RoundMatchesViewModelState.GetRoundMatches(result.matches)
            }

            is RoundMatchesUseCaseState.RoundMatchesInformation.Error -> {
                roundNumberStateFlow.value = RoundMatchesViewModelState.GenericError
            }
        }
    }

    fun getRoundNumber() {
        viewModelScope.launch {
            val result = useCase.getRoundNumber()
            handleRoundNumber(result)
        }
    }

    private fun handleRoundNumber(result: RoundMatchesUseCaseState.RoundsInformation) {
        when (result) {
            is RoundMatchesUseCaseState.RoundsInformation.GetRoundNumber -> {
                roundNumberStateFlow.value =
                    RoundMatchesViewModelState.GetRoundNumber(result.round)
            }

            is RoundMatchesUseCaseState.RoundsInformation.Error -> {
                roundNumberStateFlow.value = RoundMatchesViewModelState.GenericError
            }
        }
    }
}
