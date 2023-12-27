package com.example.koplakmungkin.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.koplakmungkin.R
import com.example.koplakmungkin.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {
    val welcomeBtn = findViewById<Button>(R.id.welcomeBtn)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        welcomeBtn.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}