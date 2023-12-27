package com.example.koplakmungkin.data.model

data class UserData(
    val user_id: String? = null,
    val email: String? = null,
    val username: String? = null,
    val token: String,
    val isLogin: Boolean = false
)