package com.example.koplakmungkin.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: DataRegister,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class DataRegister(

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("accessToken")
    val accessToken: String
)