package com.example.koplakmungkin.data.response

import com.google.gson.annotations.SerializedName

data class GradeResponse(
    @field:SerializedName("status")
    val status: Status
)
data class Status(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("message")
    val message: String
)
data class Data(

    @field:SerializedName("confidence")
    val confidence: Any,

    @field:SerializedName("class")
    val jsonMemberClass: String
)
