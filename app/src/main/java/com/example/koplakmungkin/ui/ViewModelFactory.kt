package com.example.koplakmungkin.ui

import ProfileViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.ui.login.LoginViewModel
import com.example.koplakmungkin.ui.register.PersonalDataViewModel
import com.example.koplakmungkin.ui.register.RegisterViewModel


class ViewModelFactory(private val repository: KoplakRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->{
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PersonalDataViewModel::class.java) ->{
                PersonalDataViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->{
                ProfileViewModel(repository) as T
            }
             else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}