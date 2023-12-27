package com.example.koplakmungkin.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.model.UserData
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.data.response.ProfileResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PersonalDataViewModel(private val koplakRepository: KoplakRepository): ViewModel() {
    private val _profileResult = MutableLiveData<Result<ProfileResponse>>()
    val profileResult: LiveData<Result<ProfileResponse>> get() = _profileResult

    private val _sessionResult = MutableLiveData<UserData>()
    val sessionResult: LiveData<UserData> get() = _sessionResult

    fun getSession() {
        viewModelScope.launch {
            val sessionData = koplakRepository.getSession().first()
            _sessionResult.value = sessionData
        }
    }

    fun regisProfile(token:String, imageProfile: String, fullname: String, address: String, birth: String, gender: String){
        _profileResult.value = Result.Loading

        viewModelScope.launch {
            val result = koplakRepository.regisProfile(token, imageProfile,fullname, address, birth, gender)
            _profileResult.value = result

        }
    }
}