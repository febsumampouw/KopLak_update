package com.example.koplakmungkin.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.koplakmungkin.data.response.ListDataPublish

@Dao
interface KoplakDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKoplak(koplak: List<ListDataPublish>)

    @Query("SELECT * FROM publish")
    fun getAllPublish(): PagingSource<Int, ListDataPublish>

    @Query("DELETE FROM publish")
    suspend fun deleteAll()
}