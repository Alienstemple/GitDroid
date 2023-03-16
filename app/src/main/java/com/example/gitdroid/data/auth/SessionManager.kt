package com.example.gitdroid.data.auth

import android.content.Context
import android.content.SharedPreferences
import com.example.gitdroid.R

/**
 * Служебный класс, управляет хранением OAuth2-токена для GitHubAPI
 */
class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    /**
     * Сохраняет токен в [SharedPreferences]
     * @param token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Получение токена из [SharedPreferences]
     * @return token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Удаление токена из [SharedPreferences]
     */
    fun removeAuthToken() {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, null)
        editor.apply()
    }

    companion object {
        const val USER_TOKEN = "user_token"
    }
}