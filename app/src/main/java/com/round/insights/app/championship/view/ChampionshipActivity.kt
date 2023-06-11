package com.round.insights.app.championship.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.round.insights.R
import com.round.insights.app.matches.view.RoundInsightsActivity
import com.round.insights.databinding.ActivityChampionshipBinding

class ChampionshipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChampionshipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_championship)

        init()
    }

    private fun init() {
        binding = ActivityChampionshipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.toolbar.backIcon.visibility = View.GONE
        binding.toolbar.backIcon.setOnClickListener {
            // not used yet
        }

        binding.toolbar.toolbarText.visibility = View.GONE

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
                    val intent = Intent(this, RoundInsightsActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.championship_round -> {

                }

                R.id.championship_leaderboard -> {

                }
            }
            true
        }
    }
}
