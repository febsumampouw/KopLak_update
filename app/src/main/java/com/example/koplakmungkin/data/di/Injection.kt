package com.example.koplakmungkin.data.di

import android.content.Context
import android.util.Log
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.data.retrofit.ApiConfig2
import com.example.koplakmungkin.data.pref.UserPref
import com.example.koplakmungkin.data.pref.dataStore


import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): KoplakRepository {
        val pref = UserPref.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig2.getApiService(user.token)
        Log.d("ApiConfig2", "Token for ApiService: ${user.token}")
        return KoplakRepository.getInstance(apiService, pref)
    }
}