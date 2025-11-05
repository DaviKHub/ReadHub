package com.kukareku.readonline.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kukareku.readonline.data.AuthRepository
import com.kukareku.readonline.databinding.ActivityRegisterBinding
import kotlinx.coroutines.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var b: ActivityRegisterBinding
    private val authRepo = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnRegister.setOnClickListener {
            val email = b.etEmail.text.toString()
            val pass = b.etPassword.text.toString()
            if (email.isBlank() || pass.length < 6) {
                Snackbar.make(b.root, "Enter valid email and password (≥6 chars)", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            b.progress.visibility = android.view.View.VISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                val result = withContext(Dispatchers.IO) { authRepo.register(email, pass) }
                b.progress.visibility = android.view.View.GONE
                if (result.isSuccess) {
                    Snackbar.make(b.root, "Registered successfully", Snackbar.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, com.kukareku.readonline.ui.MainActivity::class.java))
                    finish()
                } else {
                    Snackbar.make(b.root, "Error: ${result.exceptionOrNull()?.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}
