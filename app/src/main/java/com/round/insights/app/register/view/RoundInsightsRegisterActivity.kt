package com.round.insights.app.register.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.round.insights.app.login.view.RoundInsightsLoginActivity
import com.round.insights.databinding.ActivityRegisterBinding

class RoundInsightsRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButtonLogin.setOnClickListener {
            val intent = Intent(this, RoundInsightsLoginActivity::class.java)
            startActivity(intent)
        }
    }
}
