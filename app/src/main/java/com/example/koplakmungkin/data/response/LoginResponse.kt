package com.example.koplakmungkin.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: DataLogin,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataLogin(
	@field:SerializedName("user_id")
	val user_id: String,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
