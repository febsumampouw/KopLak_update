package com.example.koplakmungkin.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.koplakmungkin.ui.locationpermission.LocationPermissionActivity
import com.example.koplakmungkin.utils.SharedPreferenceManager
import com.example.koplakmungkin.databinding.ActivitySplashScreenBinding
import com.example.koplakmungkin.ui.onboarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        isFirstTime()
    }

    private fun isFirstTime(){
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferenceManager = SharedPreferenceManager(this)
            if (sharedPreferenceManager.isFirstTime) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, LocationPermissionActivity::class.java))
                finish()
            }
        },2000)
    }
}