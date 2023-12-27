package com.example.koplakmungkin.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "koplak_keys")
data class RemoteKeys (
    @PrimaryKey val id: String,
    val prevkey: Int?,
    val nextKey: Int?
)