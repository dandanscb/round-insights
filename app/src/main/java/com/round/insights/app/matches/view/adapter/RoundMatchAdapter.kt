package com.round.insights.app.matches.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.round.insights.R
import com.round.insights.app.matches.model.MatchesModel

class RoundMatchAdapter(
    private val callback: RoundMatchViewHolder.RoundInsightsMatchViewHolderCallback,
    private val data: List<MatchesModel>
) : RecyclerView.Adapter<RoundMatchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoundMatchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.adapter_round_match_information, parent, false)
        return RoundMatchViewHolder(callback, itemView)
    }

    override fun onBindViewHolder(holder: RoundMatchViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = data.size
}
