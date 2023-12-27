package com.example.koplakmungkin.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.di.Injection
import com.example.koplakmungkin.data.response.RegisterResponse
import com.example.koplakmungkin.databinding.ActivityRegisterBinding
import com.example.koplakmungkin.ui.ViewModelFactory
import com.example.koplakmungkin.ui.login.LoginActivity
import com.example.koplakmungkin.ui.opening.OpeningActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(Injection.provideRepository(this)))[RegisterViewModel::class.java]

        binding.registerLayout.backBtn.setOnClickListener {
            backBtn()
        }
        binding.registerLayout.loginTextView.setOnClickListener {
            navigateToLogin()
        }
        setupAction()
    }

    private fun setupAction() {
        binding.registerLayout.nextBtn.setOnClickListener {
            val email = binding.registerLayout.emailEditText.text.toString()
            val password = binding.registerLayout.passwordEditText.text.toString()
            val username = binding.registerLayout.usernameEditText.text.toString()

            viewModel.register(username, email, password)
            viewModel.registrationResult.observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val response: RegisterResponse = result.data

                        AlertDialog.Builder(this).apply {
                            setTitle("Mantep!")
                            setMessage(response.status)
                            setPositiveButton("Lanjut") { _, _ -> }
                            create()
                            show()
                        }
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        val errorMessage: String = result.error
                        AlertDialog.Builder(this).apply {
                            setTitle("Oops")
                            setMessage(errorMessage)
                            setPositiveButton("OKE") { _, _ -> }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun backBtn() {
        val intent = Intent(this, OpeningActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                registerLayout.progressBar.visibility = View.VISIBLE
            } else {
                registerLayout.progressBar.visibility = View.GONE
            }
        }
    }
}
