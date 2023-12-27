package com.example.koplakmungkin.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PublishResponse (
    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: DataProfile,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

@Entity(tableName = "publish")
data class ListDataPublish(

    @field:SerializedName("image_profile")
    val imageProfile: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("birth")
    val birth: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("id")
    @PrimaryKey
    val id: String,

)