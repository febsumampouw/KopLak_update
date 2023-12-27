package com.example.koplakmungkin.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.model.UserData
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val koplakRepository: KoplakRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> get() = _loginResult

    fun login(email: String, password: String) {
        _loginResult.value = Result.Loading

        viewModelScope.launch {
            val result = koplakRepository.login(email, password)
            _loginResult.value = result
            Log.d("tag", "Login berhasil")
        }
    }
}