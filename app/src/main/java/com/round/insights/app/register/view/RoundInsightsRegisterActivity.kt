package com.round.insights.app.register.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.round.insights.app.login.view.RoundInsightsLoginActivity
import com.round.insights.databinding.ActivityRegisterBinding

class RoundInsightsRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.registerButton.setOnClickListener {
            performSignUp()
        }

        binding.registerButtonLogin.setOnClickListener { startLoginActivity() }
    }

    private fun startLoginActivity() {
        val intent = Intent(this, RoundInsightsLoginActivity::class.java)
        startActivity(intent)
    }

    private fun performSignUp() {
        val inputEmail = binding.registerEmail.text.toString()
        val inputPassword = binding.registerPassword.text.toString()

        if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(
                this,
                "Please fill all fields",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startLoginActivity()
                    Toast.makeText(
                        this,
                        "Register Success",
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
