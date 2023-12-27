package com.example.koplakmungkin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.di.Injection
import com.example.koplakmungkin.data.model.UserData
import com.example.koplakmungkin.data.response.LoginResponse
import com.example.koplakmungkin.databinding.ActivityLoginBinding
import com.example.koplakmungkin.ui.ViewModelFactory
import com.example.koplakmungkin.ui.main.MainActivity
import com.example.koplakmungkin.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(Injection.provideRepository(this)))
            .get(LoginViewModel::class.java)

        setupAction()

        binding.loginLayout.registerTextView.setOnClickListener{
            navigateToRegister()
        }
    }

    private fun setupAction() {
        binding.loginLayout.loginBtn.setOnClickListener {
            val email = binding.loginLayout.emailEditText.text.toString()
            val password = binding.loginLayout.passwordEditText.text.toString()

            viewModel.login(email, password)
            viewModel.loginResult.observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val response: LoginResponse = result.data
                        AlertDialog.Builder(this).apply {
                            setTitle("Mantep!")
                            setMessage(response.status)
                            setPositiveButton("Lanjut") { _, _ -> }
                            create()
                            show()
                        }
                        val intent = Intent(this, MainActivity::class.java)
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

                    else -> {}
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                loadingLayout.progressBar.visibility = View.VISIBLE
            } else {
                loadingLayout.progressBar.visibility = View.GONE
            }
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}