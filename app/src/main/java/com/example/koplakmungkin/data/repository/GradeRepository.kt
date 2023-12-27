package com.example.koplakmungkin.data.repository

import com.example.koplakmungkin.data.response.GradeResponse
import com.example.koplakmungkin.data.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback

class GradeRepository {
    private val gradeApiService = ApiConfig.getApiService()

    fun getGrad(imageFile: MultipartBody.Part, description: RequestBody, callback: Callback<GradeResponse>){
        val call = gradeApiService.getGrade(imageFile, description)
        call.enqueue(callback)
    }

}