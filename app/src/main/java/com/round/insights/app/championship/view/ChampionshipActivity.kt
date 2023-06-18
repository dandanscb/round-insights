package com.round.insights.app.championship.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.round.insights.R
import com.round.insights.app.leaderboard.view.LeaderboardFragment
import com.round.insights.app.matches.view.RoundMatchesFragment
import com.round.insights.app.profile.view.ProfileFragment
import com.round.insights.databinding.ActivityChampionshipBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChampionshipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChampionshipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityChampionshipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(ProfileFragment.newInstance())
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profile_footer -> replaceFragment(ProfileFragment.newInstance())
                R.id.round_footer -> replaceFragment(RoundMatchesFragment.newInstance())
                R.id.leaderboard_footer -> replaceFragment(LeaderboardFragment.newInstance())
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
