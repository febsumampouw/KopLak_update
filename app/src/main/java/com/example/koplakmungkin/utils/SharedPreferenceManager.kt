package com.example.koplakmungkin.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.koplakmungkin.R

class SharedPreferenceManager (context: Context) {

    private val preference = context.getSharedPreferences(
        context.getString(R.string.app_name),AppCompatActivity.MODE_PRIVATE
    )
    private val editor = preference.edit()

    private val keyIsFirstTime = "isFirstTime"
    private val keyUserId = "user_id"
    private val keyIsLoggedIn = "is_logged_in"
    private val keyUsername = "username"
    var isFirstTime
    get() = preference.getBoolean(keyIsFirstTime,true)
    set(value) {
        editor.putBoolean(keyIsFirstTime,value)
        editor.commit()
    }
    var userId: String?
        get() = preference.getString(keyUserId, null)
        set(value) {
            editor.putString(keyUserId, value)
            editor.apply()
        }
    var username: String?
        get() = preference.getString(keyUsername, null)
        set(value) = preference.edit().putString(keyUsername, value).apply()

    var isLoggedIn: Boolean
        get() = preference.getBoolean(keyIsLoggedIn, false)
        set(value) {
            editor.putBoolean(keyIsLoggedIn, value)
            editor.apply()
        }
//    fun setLoggedOut() {
//        isLoggedIn = false
//        userId = null
//    }
}