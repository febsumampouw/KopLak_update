package com.example.koplakmungkin.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: DataProfile,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataProfile(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("profile_id")
	val profileId: String,

	@field:SerializedName("image_profile")
	val imageProfile: String,

	@field:SerializedName("birth")
	val birth: String,

	@field:SerializedName("fullname")
	val fullname: String
)
