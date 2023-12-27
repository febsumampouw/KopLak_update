package com.example.koplakmungkin.data.response

data class UserProfileResponse(
	val code: Int,
	val data: List<DataItem>,
	val message: String,
	val status: String
)

data class DataItem(
	val role: String,
	val address: String,
	val gender: String,
	val updatedAt: String,
	val userId: String,
	val profileId: String,
	val imageProfile: String,
	val birth: String,
	val createdAt: String,
	val fullname: String
)

