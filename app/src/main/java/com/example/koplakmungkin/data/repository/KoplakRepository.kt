package com.example.koplakmungkin.data.repository

import android.util.Log
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.model.UserData
import com.example.koplakmungkin.data.pref.UserPref
import com.example.koplakmungkin.data.response.ErrorResponse
import com.example.koplakmungkin.data.response.LoginResponse
import com.example.koplakmungkin.data.response.ProfileResponse
import com.example.koplakmungkin.data.response.RegisterResponse
import com.example.koplakmungkin.data.response.UserProfileResponse
import com.example.koplakmungkin.data.retrofit.ApiService
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response

class KoplakRepository private constructor(
    private val apiService: ApiService,
    private val userPref: UserPref
) {

    suspend fun saveSession(user: UserData) {
        userPref.saveSession(user)
    }

    fun getSession(): Flow<UserData> {
        return userPref.getSession()
    }

    suspend fun logout() {
        userPref.logout()
    }

    suspend fun register(username: String, email: String, password: String): Flow<Result<RegisterResponse>> = flow {
        try {
            val response = apiService.register(username, email, password)

            if (response.status == "BAD_REQUEST") {
                emit(Result.Error(response.status))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: HttpException) {
            emit(handleHttpException(e))
        }
    }

    suspend fun regisProfile(
        token: String,
        imageProfile: String,
        fullname: String,
        address: String,
        birth: String,
        gender: String
    ): Result<ProfileResponse> {
        return try {
            val response = apiService.profile(token, imageProfile, fullname, address, birth, gender)

            if (response.status == "BAD_REQUEST") {
                Log.d("tag", "regisprofile bad")
                Result.Error(response.status)
            } else {
                Log.d("tag", "regisprofile success")
                Result.Success(response)
            }
        } catch (e: HttpException) {
            return handleHttpException(e)
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(email, password)

            if (response.status == "BAD_REQUEST") {
                Result.Error(response.status)
            } else {
                val userData = UserData(user_id = response.data.user_id, email = email, token = response.data.accessToken, isLogin = true)
                saveSession(userData)
                Result.Success(response)
            }
        } catch (e: HttpException) {
            return handleHttpException(e)
        }
    }

    private fun handleHttpException(e: HttpException): Result<Nothing> {
        return if (e.response()?.errorBody() != null) {
            try {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                Result.Error(errorBody.message ?: "Unknown error")
            } catch (jsonException: JsonSyntaxException) {
                Result.Error("JSON parsing error")
            }
        } else {
            Result.Error("Unknown error")
        }
    }

    suspend fun getUserProfile(token: String, userId: String): Response<UserProfileResponse> {
        return apiService.getUserProfile(token, userId)
    }

    companion object {
        private var instance: KoplakRepository? = null

        fun getInstance(apiService: ApiService, userPref: UserPref): KoplakRepository {
            return instance ?: synchronized(this) {
                instance ?: KoplakRepository(apiService, userPref).also { instance = it }
            }
        }
    }
}
