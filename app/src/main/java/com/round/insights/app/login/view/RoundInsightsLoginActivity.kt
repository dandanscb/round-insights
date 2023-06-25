package com.round.insights.app.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.round.insights.app.championship.view.ChampionshipActivity
import com.round.insights.app.register.view.RoundInsightsRegisterActivity
import com.round.insights.commons.model.RoundInsightsUser
import com.round.insights.databinding.ActivityLoginBinding
import java.lang.Exception

class RoundInsightsLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            displayToast("Please fill all fields.")
            return
        }

        auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loginIsSuccessful()
                } else {
                    loginAuthenticationFailed()
                }
            }.addOnFailureListener {
                loginOnFailure(it)
            }
    }

    private fun loginIsSuccessful() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("user").document(userId)

        ref.get().addOnSuccessListener {
            it?.let {
                val user = RoundInsightsUser(
                    name = it.data?.get("name")?.toString() ?: "",
                    nickname = it.data?.get("nickname")?.toString() ?: "",
                    email = it.data?.get("email")?.toString() ?: ""
                )
                val intent = Intent(this, ChampionshipActivity::class.java)
                startActivity(intent)
            }
        }
            .addOnFailureListener { displayToast("Failed!") }
    }

    private fun loginAuthenticationFailed() {
        displayToast("Authentication failed.")
    }

    private fun loginOnFailure(exception: Exception) {
        displayToast("Error occurred ${exception.localizedMessage}")
    }

    private fun displayToast(text: String) {
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_SHORT,
        ).show()
    }
}
