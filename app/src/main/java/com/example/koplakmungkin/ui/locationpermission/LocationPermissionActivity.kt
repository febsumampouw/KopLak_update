package com.example.koplakmungkin.ui.locationpermission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.koplakmungkin.ui.opening.OpeningActivity
import com.example.koplakmungkin.R
import com.example.koplakmungkin.databinding.ActivityLocationPermissionBinding


class LocationPermissionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLocationPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this@LocationPermissionActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                navigateToMainActivity()
            }
        }

        binding.permissionBtn.setOnClickListener{requestLocationPermission()}

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                navigateToMainActivity()
            } else {
                Toast.makeText(
                    this@LocationPermissionActivity,
                    R.string.permission_denied,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun navigateToMainActivity() {
        val homeIntent = Intent(this, OpeningActivity::class.java)
        startActivity(homeIntent)
        finish()
    }
}