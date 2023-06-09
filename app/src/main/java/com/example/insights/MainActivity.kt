package com.example.insights

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.insights.databinding.ActivityMainBinding
import com.example.insights.ui.MainViewModel
import com.example.insights.ui.RoundInsightsViewModelState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainScope = MainScope()
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainScope.launch {
            mainViewModel.roundNumberViewState.collect {
                when (it) {
                    is RoundInsightsViewModelState.RoundNumber.GetRoundNumber -> {
                        mainViewModel.getRoundMatches(it.roundNumber)
                    }
                    is RoundInsightsViewModelState.RoundNumber.Error -> {
                        // not used yet
                    }
                    is RoundInsightsViewModelState.RoundNumber.GetRoundMatches -> {
                        it.matches
                    }
                    is RoundInsightsViewModelState.RoundNumber.ABC -> {
                        // not used yet
                    }
                }
            }

            mainViewModel.roundMatchesViewStateFlow.collect {
                when (it) {
                    is RoundInsightsViewModelState.RoundMatches.GetRoundMatches -> {
                        it.matches
                    }
                    is RoundInsightsViewModelState.RoundMatches.Error -> {
                        // not used yet
                    }
                }
            }
        }

        mainViewModel.getRoundNumber()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}
