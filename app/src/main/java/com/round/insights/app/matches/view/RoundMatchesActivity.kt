package com.round.insights.app.matches.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView
import com.round.insights.R
import com.round.insights.app.matches.model.RoundMatchesModel
import com.round.insights.app.matches.view.adapter.RoundMatchAdapter
import com.round.insights.app.matches.view.adapter.RoundMatchViewHolder
import com.round.insights.app.matches.viewmodel.RoundMatchesViewModel
import com.round.insights.app.matches.viewmodel.RoundMatchesViewModelState
import com.round.insights.databinding.ActivityRoundMatchesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class RoundMatchesActivity : AppCompatActivity(),
    RoundMatchViewHolder.RoundInsightsMatchViewHolderCallback {

    private lateinit var binding: ActivityRoundMatchesBinding

    private val mainScope = MainScope()
    private val roundMatchesViewModel: RoundMatchesViewModel by viewModels()

    private lateinit var adapter: RoundMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initViewModel()

        roundMatchesViewModel.getRoundNumber()
    }

    private fun init() {
        binding = ActivityRoundMatchesBinding.inflate(layoutInflater)
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
                    // not used yet
                }

                R.id.championship_round -> {
                    // not used yet
                }

                R.id.championship_leaderboard -> {
                    // not used yet
                }
            }
            true
        }
    }

    private fun initViewModel() {
        mainScope.launch {
            roundMatchesViewModel.roundNumberViewState.collect {
                when (it) {
                    is RoundMatchesViewModelState.GetRoundNumber -> {
                        roundMatchesViewModel.getRoundMatches(it.roundNumber)
                    }

                    is RoundMatchesViewModelState.GetRoundMatches -> {
                        loadMatches(it.matches)
                        binding.shimmer.root.visibility = View.GONE
                    }

                    is RoundMatchesViewModelState.GenericError -> {
                        // not used yet
                    }
                }
            }
        }
    }

    private fun loadMatches(matches: RoundMatchesModel) {
        adapter = RoundMatchAdapter(this, matches.matches)

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

    override fun setUrlImageRadioButton(
        urlImage: String,
        svgImageView: SVGImageView,
        radioButton: RadioButton
    ) {
        Thread {
            val url = URL(urlImage)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val inputStream: InputStream = connection.inputStream
            val svg = SVG.getFromInputStream(inputStream)

            runOnUiThread {
                svgImageView.setSVG(svg)
                setCompoundDrawablesWithIntrinsicBounds(svgImageView, radioButton)
            }
        }.start()
    }

    private fun setCompoundDrawablesWithIntrinsicBounds(
        svgImageView: SVGImageView,
        radioButton: RadioButton
    ) {
        val desiredWidth =
            resources.getDimensionPixelSize(R.dimen.round_insights_52dp)
        val desiredHeight =
            resources.getDimensionPixelSize(R.dimen.round_insights_52dp)
        svgImageView.scaleType = ImageView.ScaleType.FIT_CENTER
        svgImageView.adjustViewBounds = true
        svgImageView.maxWidth = desiredWidth
        svgImageView.maxHeight = desiredHeight

        val bitmap =
            Bitmap.createBitmap(desiredWidth, desiredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        svgImageView.draw(canvas)
        val drawable = BitmapDrawable(resources, bitmap)
        val topMargin = resources.getDimensionPixelSize(R.dimen.round_insights_2dp)
        val layerDrawable = LayerDrawable(arrayOf(drawable))
        layerDrawable.setLayerInset(ZERO, ZERO, topMargin, ZERO, ZERO)
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, layerDrawable, null, null)
    }

    companion object {
        private const val ZERO = 0
    }
}
