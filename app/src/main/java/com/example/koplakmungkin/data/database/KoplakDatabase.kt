package com.example.koplakmungkin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.koplakmungkin.data.response.ListDataPublish

@Database(
    entities = [ListDataPublish::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class KoplakDatabase : RoomDatabase(){
    abstract fun publishDao() : KoplakDao
    abstract fun remoteKeysDao() : RemoteKeysDao
    companion object{
        @Volatile
        private var INSTANCE: KoplakDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): KoplakDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    KoplakDatabase::class.java, "koplak_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}