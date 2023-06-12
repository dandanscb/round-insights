package com.round.insights.app.matches.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView
import com.round.insights.R
import com.round.insights.app.matches.model.RoundMatchesModel
import com.round.insights.app.matches.view.adapter.RoundInsightsMatchAdapter
import com.round.insights.app.matches.view.adapter.RoundInsightsMatchViewHolder
import com.round.insights.app.matches.viewmodel.RoundInsightsViewModel
import com.round.insights.app.matches.viewmodel.RoundInsightsViewModelState
import com.round.insights.databinding.ActivityRoundInsightsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class RoundInsightsActivity : AppCompatActivity(),
    RoundInsightsMatchViewHolder.RoundInsightsMatchViewHolderCallback {

    private lateinit var binding: ActivityRoundInsightsBinding

    private val mainScope = MainScope()
    private val roundInsightsViewModel: RoundInsightsViewModel by viewModels()

    private lateinit var adapter: RoundInsightsMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initViewModel()

        roundInsightsViewModel.getRoundNumber()
    }

    private fun init() {
        binding = ActivityRoundInsightsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.toolbarText.visibility = View.GONE
        binding.toolbar.menuIcon.visibility = View.GONE

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.toolbar.backIcon.setOnClickListener {
            onBackPressed()
        }
        binding.toolbar.menuIcon.setOnClickListener {
            if (binding.navigationView.isVisible) {
                binding.navigationView.visibility = View.GONE
            } else {
                binding.navigationView.visibility = View.VISIBLE
            }
        }

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.championship_brasileirao -> {

                }
                R.id.championship_round -> {

                }
                R.id.championship_leaderboard -> {

                }
            }
            true
        }
    }

    private fun initViewModel() {
        mainScope.launch {
            roundInsightsViewModel.roundNumberViewState.collect {
                when (it) {
                    is RoundInsightsViewModelState.GetRoundNumber -> {
                        roundInsightsViewModel.getRoundMatches(it.roundNumber)
                    }

                    is RoundInsightsViewModelState.GetRoundMatches -> {
                        loadMatches(it.matches)
                        binding.shimmer.root.visibility = View.GONE
                    }

                    is RoundInsightsViewModelState.GenericError -> {
                        // not used yet
                    }
                }
            }
        }
    }

    private fun loadMatches(matches: RoundMatchesModel) {
        adapter = RoundInsightsMatchAdapter(this, matches.matches)

        binding.matchesList.layoutManager = LinearLayoutManager(this)
        binding.matchesList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    override fun setUrlImage(urlImage: String, svgImageView: SVGImageView) {
        Thread {
            val url = URL(urlImage)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val inputStream: InputStream = connection.inputStream
            val svg = SVG.getFromInputStream(inputStream)

            runOnUiThread {
                svgImageView.setSVG(svg)
            }
        }.start()
    }
}