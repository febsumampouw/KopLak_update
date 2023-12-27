package com.example.koplakmungkin.ui.opening

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koplakmungkin.databinding.ActivityOpeningBinding
import com.example.koplakmungkin.ui.login.LoginActivity
import com.example.koplakmungkin.ui.register.RegisterActivity

class OpeningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpeningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener{
            loginScreenIntent()
        }

        binding.registerBtn.setOnClickListener{
            registerScreenIntent()
        }
    }

    private fun loginScreenIntent() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun registerScreenIntent() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}