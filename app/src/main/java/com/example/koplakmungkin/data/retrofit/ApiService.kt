package com.example.koplakmungkin.data.retrofit

import com.example.koplakmungkin.data.response.GradeResponse
import com.example.koplakmungkin.data.response.LoginResponse
import com.example.koplakmungkin.data.response.ProfileResponse
import com.example.koplakmungkin.data.response.RegisterResponse
import com.example.koplakmungkin.data.response.UserProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("predict")
    fun getGrade(
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<GradeResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("profile")
    suspend fun profile(
        @Header("Authorization") token: String,
        @Field("image_profile") imageProfile: String,
        @Field("fullname") fullName: String,
        @Field("address") address: String,
        @Field("birth") birth: String,
        @Field("gender") gender: String
    ): ProfileResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("profile/{user_id}")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("user_id") userId: String
    ): Response<UserProfileResponse>
}
