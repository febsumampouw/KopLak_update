package com.example.koplakmungkin.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.model.UserData
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.data.response.RegisterResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(private val koplakRepository: KoplakRepository) : ViewModel() {
    private val _registrationResult = MutableLiveData<Result<RegisterResponse>>()
    val registrationResult: LiveData<Result<RegisterResponse>> get() = _registrationResult

    fun register(username: String, email: String, password: String) {
        _registrationResult.value = Result.Loading

        viewModelScope.launch {
            koplakRepository.register(username, email, password).collect { result ->
                when (result) {
                    is Result.Success -> {
                        val response: RegisterResponse = result.data
                        val user = UserData(
                            token = response.data.accessToken,
                            email = response.data.email,
                            username = response.data.username
                        )
                        koplakRepository.saveSession(user)
                    }
                    else -> {}
                }
                _registrationResult.value = result
            }
        }
    }
}
