package com.round.insights.app.championship.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.round.insights.R
import com.round.insights.app.leaderboard.view.LeaderboardFragment
import com.round.insights.app.matches.view.RoundMatchesFragment
import com.round.insights.app.profile.view.ProfileFragment
import com.round.insights.commons.model.RoundInsightsUser
import com.round.insights.databinding.ActivityChampionshipBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChampionshipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChampionshipBinding
    private lateinit var user: RoundInsightsUser

    private lateinit var profileFragment: Fragment
    private lateinit var roundMatchesFragment: Fragment
    private lateinit var leaderboardFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initExtras()
        init()
    }

    private fun initExtras() {
        intent.extras?.let { bundle ->
            user = bundle.get(ROUND_INSIGHTS_USER) as RoundInsightsUser
        }
    }

    private fun init() {
        binding = ActivityChampionshipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileFragment = ProfileFragment.newInstance(user)
        roundMatchesFragment = RoundMatchesFragment.newInstance()
        leaderboardFragment = LeaderboardFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, profileFragment)
            .commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, roundMatchesFragment)
            .commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, leaderboardFragment)
            .commit()

        supportFragmentManager.beginTransaction()
            .hide(profileFragment)
            .hide(roundMatchesFragment)
            .hide(leaderboardFragment)
            .show(profileFragment)
            .commit();

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.bottomNavigation.setOnItemSelectedListener {
            val selectedFragment = when (it.itemId) {
                R.id.profile_footer -> profileFragment
                R.id.round_footer -> roundMatchesFragment
                R.id.leaderboard_footer -> leaderboardFragment
                else -> {
                    profileFragment
                }
            }
            supportFragmentManager.beginTransaction()
                .hide(profileFragment)
                .hide(roundMatchesFragment)
                .hide(leaderboardFragment)
                .show(selectedFragment)
                .commit();

            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        private const val ROUND_INSIGHTS_USER = "user"

        @JvmStatic
        fun newInstance(context: Context, user: RoundInsightsUser): Intent =
            Intent(context, ChampionshipActivity::class.java).apply {
                putExtra(ROUND_INSIGHTS_USER, user)
            }
    }
}
