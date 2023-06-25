package com.round.insights.app.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.round.insights.app.championship.view.ChampionshipActivity
import com.round.insights.app.register.view.RoundInsightsRegisterActivity
import com.round.insights.databinding.ActivityLoginBinding

class RoundInsightsLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.loginButton.setOnClickListener {
            performLogin()
        }

        binding.loginButtonRegister.setOnClickListener {
            val intent = Intent(this, RoundInsightsRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin() {
        val inputEmail = binding.loginEmail.text.toString()
        val inputPassword = binding.loginPassword.text.toString()

        if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(
                this,
                "Please fill all fields",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, ChampionshipActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext,
                        "Login Success.",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Error occurred ${it.localizedMessage}",
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }
}
