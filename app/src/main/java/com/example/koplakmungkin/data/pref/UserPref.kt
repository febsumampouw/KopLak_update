package com.example.koplakmungkin.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.koplakmungkin.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPref private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserData) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = user.user_id ?: ""
            preferences[EMAIL_KEY] = user.email ?: ""
            preferences[USERNAME_KEY] = user.username ?: ""
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserData> {
        return dataStore.data.map { preferences ->
            UserData(
                preferences[USER_ID_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[USERNAME_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPref? = null

        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPref {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPref(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
