package com.round.insights.app.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.caverock.androidsvg.SVGImageView
import com.round.insights.app.model.MatchesModel
import com.round.insights.databinding.AdapterRoundMatchInformationBinding

class RoundInsightsMatchViewHolder(
    private val callback: RoundInsightsMatchViewHolderCallback,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: AdapterRoundMatchInformationBinding

    fun bind(data: MatchesModel) {
        binding.roundMatchDate.text = data.matchDate
        binding.roundMatchStadium.text = data.stadium.stadiumName
        binding.roundMatchHour.text = data.matchHour

        binding.homeTeamAcronym.text = data.homeTeam.teamAcronym
        callback.setUrlImage(data.homeTeam.teamCrest, binding.homeTeamCrest)

        binding.outTeamAcronym.text = data.outTeam.teamAcronym
        callback.setUrlImage(data.outTeam.teamCrest, binding.outTeamCrest)
    }

    interface RoundInsightsMatchViewHolderCallback {
        fun setUrlImage(urlImage: String, svgImageView: SVGImageView)
    }
}
