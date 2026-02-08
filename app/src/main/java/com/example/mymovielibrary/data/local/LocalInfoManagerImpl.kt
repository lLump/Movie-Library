package com.example.mymovielibrary.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.mymovielibrary.data.local.storage.Store
import com.example.mymovielibrary.data.local.storage.Store.clear
import com.example.mymovielibrary.domain.local.LocalInfoManager

class LocalInfoManagerImpl(context: Context): LocalInfoManager { //todo (Create interface)
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
    private val editor = sharedPrefs.edit()

    override fun clearInfo() { // when logout
        Store.tmdbData.clear()
        editor.clear().apply()
    }

    override fun saveUserInfo(accountIdV4: String, sessionId: String, accessToken: String) {
        saveUserInfoIntoPrefs(accountIdV4, sessionId, accessToken)
        saveUserInfoIntoSingleton(accountIdV4, sessionId, accessToken)
    }

    fun loadUserInfoFromPrefsToSingletonIfExist() { // for MainActivity
        getInfoIfExist { isSaved, accountId, sessionId, token ->
            if (isSaved) {
                saveUserInfoIntoSingleton(accountId, sessionId, token) //TODO language ISO
            }
        }
    }

    private fun saveUserInfoIntoSingleton(accountIdV4: String, sessionId: String, accessToken: String) {
        Store.run {
            this.tmdbData.accountIdV4 = accountIdV4
            this.tmdbData.sessionId = sessionId
            this.tmdbData.accessToken = accessToken
        }
    }

    private fun saveUserInfoIntoPrefs(accountIdV4: String, sessionId: String, accessToken: String) {
        editor.run {
            putString("account_id_v4", accountIdV4)
            putString("session_id", sessionId)
            putString("access_token", accessToken)
            apply()
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