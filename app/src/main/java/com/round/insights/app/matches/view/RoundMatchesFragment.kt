package com.round.insights.app.matches.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView
import com.round.insights.R
import com.round.insights.app.matches.model.RoundMatchesModel
import com.round.insights.app.matches.view.adapter.RoundMatchAdapter
import com.round.insights.app.matches.view.adapter.RoundMatchViewHolder
import com.round.insights.app.matches.viewmodel.RoundMatchesViewModel
import com.round.insights.app.matches.viewmodel.RoundMatchesViewModelFactory
import com.round.insights.app.matches.viewmodel.RoundMatchesViewModelState
import com.round.insights.databinding.FragmentRoundMatchesBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class RoundMatchesFragment : Fragment(),
    RoundMatchViewHolder.RoundInsightsMatchViewHolderCallback {

    private lateinit var binding: FragmentRoundMatchesBinding

    private lateinit var adapter: RoundMatchAdapter
    private var roundNumber: String = FIRST_ROUND
    private var roundMatchesCache: MutableList<RoundMatchesModel> = mutableListOf()

    private var viewModel: RoundMatchesViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoundMatchesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModel()
        initObservable()
    }

    private fun init() {
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.selectRoundBack.setOnClickListener {
            val previousRound = roundNumber.toInt() - ONE
            checkIfNeedToFetchAnotherRound(previousRound)
        }
        binding.selectRoundForward.setOnClickListener {
            val nextRound = roundNumber.toInt() + ONE
            checkIfNeedToFetchAnotherRound(nextRound)
        }
    }

    private fun initViewModel() {
        val factory = RoundMatchesViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(RoundMatchesViewModel::class.java)

        viewModel?.getRoundNumber()
    }

    private fun initObservable() {
        viewModel?.viewState?.observe(viewLifecycleOwner) {
            when (it) {
                is RoundMatchesViewModelState.GetRoundNumber -> {
                    configureRoundSelectButtons(it.roundNumber)
                    binding.selectRoundText.text = getString(
                        R.string.round_insights_round_matches_round_number,
                        it.roundNumber
                    )
                    viewModel?.getRoundMatches(it.roundNumber)
                }

                is RoundMatchesViewModelState.GetRoundMatches -> {
                    roundMatchesCache.add(it.matches)
                    handleGetRoundMatches(matches = it.matches)
                }

                is RoundMatchesViewModelState.GenericError -> {
                    // not used yet
                }
            }
        }
    }

    private fun handleGetRoundMatches(matches: RoundMatchesModel) {
        configureRoundSelectButtons(roundNumber)
        binding.selectRoundText.text = getString(
            R.string.round_insights_round_matches_round_number,
            roundNumber
        )
        loadMatches(matches)
        hideLoading()
    }

    private fun configureRoundSelectButtons(roundNumber: String) {
        if (roundNumber == FIRST_ROUND) {
            binding.selectRoundBack.isFocusable = false
            binding.selectRoundBack.isClickable = false
            binding.selectRoundBack.visibility = View.INVISIBLE
        } else {
            binding.selectRoundBack.isFocusable = true
            binding.selectRoundBack.isClickable = true
            binding.selectRoundBack.visibility = View.VISIBLE
        }

        if (roundNumber == LAST_ROUND) {
            binding.selectRoundForward.isFocusable = false
            binding.selectRoundForward.isClickable = false
            binding.selectRoundForward.visibility = View.INVISIBLE
        } else {
            binding.selectRoundForward.isFocusable = true
            binding.selectRoundForward.isClickable = true
            binding.selectRoundForward.visibility = View.VISIBLE
        }

        this.roundNumber = roundNumber
    }

    private fun checkIfNeedToFetchAnotherRound(newRoundNumber: Int) {
        val round = roundMatchesCache.find { it.roundNumber == newRoundNumber }
        this.roundNumber = newRoundNumber.toString()
        showLoading()

        round?.let {
            handleGetRoundMatches(it)
        } ?: run {
            viewModel?.getRoundMatches(this.roundNumber)
        }
    }

    private fun showLoading() {
        binding.scrollView.visibility = View.GONE
        binding.shimmer.root.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.selectRoundContainer.visibility = View.VISIBLE
        binding.scrollView.visibility = View.VISIBLE
        binding.shimmer.root.visibility = View.GONE
        binding.selectRoundShimmer.visibility = View.GONE
    }

    private fun loadMatches(matches: RoundMatchesModel) {
        adapter = RoundMatchAdapter(this, matches.matches)

        binding.matchesList.layoutManager = LinearLayoutManager(this.requireActivity())
        binding.matchesList.adapter = adapter
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

            this.requireActivity().runOnUiThread {
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
        val topMargin = resources.getDimensionPixelSize(R.dimen.round_insights_4dp)
        val layerDrawable = LayerDrawable(arrayOf(drawable))
        layerDrawable.setLayerInset(ZERO, ZERO, topMargin, ZERO, ZERO)
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, layerDrawable, null, null)
    }

    companion object {
        private const val FIRST_ROUND = "1"
        private const val LAST_ROUND = "38"
        private const val ZERO = 0
        private const val ONE = 1

        @JvmStatic
        fun newInstance(): Fragment = RoundMatchesFragment().also() {
        }
    }
}
