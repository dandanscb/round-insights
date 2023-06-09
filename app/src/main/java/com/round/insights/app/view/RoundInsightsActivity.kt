package com.round.insights.app.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.round.insights.app.viewmodel.RoundInsightsViewModel
import com.round.insights.app.viewmodel.RoundInsightsViewModelState
import com.round.insights.databinding.ActivityRoundInsightsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoundInsightsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoundInsightsBinding
    private val mainScope = MainScope()
    private val roundInsightsViewModel: RoundInsightsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoundInsightsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainScope.launch {
            roundInsightsViewModel.roundNumberViewState.collect {
                when (it) {
                    is RoundInsightsViewModelState.GetRoundNumber -> {
                        roundInsightsViewModel.getRoundMatches(it.roundNumber)
                    }
                    is RoundInsightsViewModelState.GetRoundMatches -> {
                        it.matches
                    }
                    is RoundInsightsViewModelState.GenericError -> {
                        // not used yet
                    }
                }
            }
        }

        roundInsightsViewModel.getRoundNumber()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}
