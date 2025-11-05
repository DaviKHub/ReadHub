package com.kukareku.readonline.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kukareku.readonline.R
import com.kukareku.readonline.data.repository.RemoteRepository
import com.kukareku.readonline.databinding.ActivityLoginBinding
import com.kukareku.readonline.databinding.ActivityRegisterBinding
import com.kukareku.readonline.utils.snack
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var b: ActivityLoginBinding

    private lateinit var repo: RemoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        repo = RemoteRepository(applicationContext)

        b.btnLogin.setOnClickListener {
            val email = b.etEmail.text?.toString()?.trim() ?: ""
            val password = b.etPassword.text?.toString() ?: ""
            if (email.isEmpty() || password.length < 6) {
                container.snack(getString(R.string.invalid_input))
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.Main).launch {
                val response = withContext(Dispatchers.IO) { repo.login(email, password) }
                if (response.isSuccessful && response.body()?.success == true) {
                    val token = response.body()?.token ?: ""
                    repo.saveToken(token)
                    startActivity(Intent(this@LoginActivity, com.kukareku.readonline.ui.MainActivity::class.java))
                    finish()
                } else {
                    container.snack(response.body()?.message ?: getString(R.string.invalid_credentials))
                }
            }
        }

        b.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
