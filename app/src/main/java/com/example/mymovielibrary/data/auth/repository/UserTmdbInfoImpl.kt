package com.example.mymovielibrary.data.auth.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.mymovielibrary.data.storage.Store
import com.example.mymovielibrary.data.storage.Store.clear

class UserTmdbInfoImpl(context: Context) {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
    private val editor = sharedPrefs.edit()

    fun clearInfo() {
        Store.tmdbData.clear()
        editor.clear().apply()
    }

    fun saveUserInfo(accountIdV4: String, sessionId: String, accessToken: String) {
        editor.run {
            putString("account_id_v4", accountIdV4)
            putString("session_id", sessionId)
            putString("access_token", accessToken)
            apply()
        }
    }

    fun getLocalSaveUserInfoIfExist() {
        getInfoIfExist { isSaved, accountId, sessionId, token ->
            if (isSaved) {
                Store.run {
                    this.tmdbData.accountIdV4 = accountId
                    this.tmdbData.sessionId = sessionId
                    this.tmdbData.accessToken = token
                    //TODO language ISO
                }
            }
        }
    }

    private fun getInfoIfExist(info: (Boolean, String, String, String) -> Unit) {
        val accountId = sharedPrefs.getString("account_id_v4", "noId") as String
        val sessionId = sharedPrefs.getString("session_id", "noSessionId") as String
        val accessToken = sharedPrefs.getString("access_token", "noToken") as String

        //fixme (always true)
        if (accountId.isNotEmpty() || sessionId.isNotEmpty() || accessToken.isNotEmpty()) {
            info(true, accountId, sessionId, accessToken)
        } else info(false, accountId, sessionId, accessToken)
    }
}