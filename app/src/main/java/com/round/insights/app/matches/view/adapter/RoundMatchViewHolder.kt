package com.round.insights.app.matches.view.adapter

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caverock.androidsvg.SVGImageView
import com.round.insights.R
import com.round.insights.app.matches.model.MatchesModel

class RoundMatchViewHolder(
    private val callback: RoundInsightsMatchViewHolderCallback,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val roundMatchDate: TextView = itemView.findViewById(R.id.round_match_date)
    private val roundMatchStadium: TextView = itemView.findViewById(R.id.round_match_stadium)

    private val homeTeamAcronym: TextView = itemView.findViewById(R.id.home_team_acronym)
    private val homeTeamCrest: SVGImageView = itemView.findViewById(R.id.home_team_crest)
    private val outTeamAcronym: TextView = itemView.findViewById(R.id.out_team_acronym)
    private val outTeamCrest: SVGImageView = itemView.findViewById(R.id.out_team_crest)
    private val radioButton: RadioButton = itemView.findViewById(R.id.radioButton)

    fun bind(data: MatchesModel) {
        roundMatchDate.text = "${data.matchDate}$SPACE${data.matchHour}"
        roundMatchStadium.text = data.stadium.stadiumName

        homeTeamAcronym.text = data.homeTeam.teamAcronym
        callback.setUrlImageRadioButton(data.homeTeam.teamCrest, homeTeamCrest, radioButton)
        radioButton.setCompoundDrawablesWithIntrinsicBounds(homeTeamCrest.drawable, null, null, null)

        outTeamAcronym.text = data.outTeam.teamAcronym
        callback.setUrlImage(data.outTeam.teamCrest, outTeamCrest)

    }

    interface RoundInsightsMatchViewHolderCallback {
        fun setUrlImage(urlImage: String, svgImageView: SVGImageView)
        fun setUrlImageRadioButton(urlImage: String, svgImageView: SVGImageView, radioButton: RadioButton)
    }

    companion object {
        private const val SPACE = " "
    }
}
