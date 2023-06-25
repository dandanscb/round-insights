package com.round.insights.app.register.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.round.insights.app.login.view.RoundInsightsLoginActivity
import com.round.insights.databinding.ActivityRegisterBinding
import java.lang.Exception

class RoundInsightsRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val inputName = binding.registerName.text.toString()
        val inputNickname = binding.registerNickname.text.toString()
        val inputEmail = binding.registerEmail.text.toString()
        val inputPassword = binding.registerPassword.text.toString()

        if (inputEmail.isEmpty() || inputPassword.isEmpty() || inputName.isEmpty() || inputNickname.isEmpty()) {
            displayToast("Please fill all fields.")
            return
        }

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    registerIsSuccessful(inputName, inputNickname, inputEmail)
                } else {
                    registerIsNotSuccessful()
                }
            }
            .addOnFailureListener {
                registerOnFailure(it)
            }
    }

    private fun registerIsSuccessful(inputName: String, inputNickname: String, inputEmail: String) {
        val userMap = hashMapOf(
            "name" to inputName,
            "nickname" to inputNickname,
            "email" to inputEmail
        )

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("user").document(userId).set(userMap)
            .addOnSuccessListener { displayToast("Successfully Added!") }
            .addOnFailureListener { displayToast("Failed Added!") }

        startLoginActivity()
        displayToast("Register Success.")
    }

    private fun registerIsNotSuccessful() {
        displayToast("Authentication failed.")
    }

    private fun registerOnFailure(exception: Exception) {
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
