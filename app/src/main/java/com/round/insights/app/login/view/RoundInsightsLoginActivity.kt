package com.round.insights.app.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.round.insights.app.register.view.RoundInsightsRegisterActivity
import com.round.insights.databinding.ActivityLoginBinding

class RoundInsightsLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButtonRegister.setOnClickListener {
            val intent = Intent(this, RoundInsightsRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
