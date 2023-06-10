package com.round.insights.app.view.adapter

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caverock.androidsvg.SVGImageView
import com.round.insights.R
import com.round.insights.app.model.MatchesModel

class RoundInsightsMatchViewHolder(
    private val callback: RoundInsightsMatchViewHolderCallback,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val roundMatchDate: TextView = itemView.findViewById(R.id.round_match_date)
    private val roundMatchStadium: TextView = itemView.findViewById(R.id.round_match_stadium)
    private val roundMatchHour: TextView = itemView.findViewById(R.id.round_match_hour)

    private val homeTeamAcronym: TextView = itemView.findViewById(R.id.home_team_acronym)
    private val homeTeamCrest: SVGImageView = itemView.findViewById(R.id.home_team_crest)
    private val outTeamAcronym: TextView = itemView.findViewById(R.id.out_team_acronym)
    private val outTeamCrest: SVGImageView = itemView.findViewById(R.id.out_team_crest)

    private val radioGroup: RadioGroup = itemView.findViewById(R.id.radio_group_guess)
    private val homeTeamRadioButton: RadioButton = itemView.findViewById(R.id.home_team_guess)
    private val outTeamRadioButton: RadioButton = itemView.findViewById(R.id.out_team_guess)
    private val tieRadioButton: RadioButton = itemView.findViewById(R.id.tie_guess)

    fun bind(data: MatchesModel) {
        roundMatchDate.text = data.matchDate
        roundMatchStadium.text = data.stadium.stadiumName
        roundMatchHour.text = data.matchHour

        homeTeamAcronym.text = data.homeTeam.teamAcronym
        callback.setUrlImage(data.homeTeam.teamCrest, homeTeamCrest)

        outTeamAcronym.text = data.outTeam.teamAcronym
        callback.setUrlImage(data.outTeam.teamCrest, outTeamCrest)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // not used yet
        }
    }

    interface RoundInsightsMatchViewHolderCallback {
        fun setUrlImage(urlImage: String, svgImageView: SVGImageView)
    }
}
