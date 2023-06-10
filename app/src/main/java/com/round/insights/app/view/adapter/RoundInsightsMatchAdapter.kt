package com.round.insights.app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.round.insights.R
import com.round.insights.app.model.MatchesModel

class RoundInsightsMatchAdapter(
    private val callback: RoundInsightsMatchViewHolder.RoundInsightsMatchViewHolderCallback,
    private val data: List<MatchesModel>
) : RecyclerView.Adapter<RoundInsightsMatchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoundInsightsMatchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.adapter_round_match_information, parent, false)
        return RoundInsightsMatchViewHolder(callback, itemView)
    }

    override fun onBindViewHolder(holder: RoundInsightsMatchViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = data.size
}
