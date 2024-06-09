package com.example.mymovielibrary.data.auth.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.auth.repository.LocalUserInfo

class UserTmdbInfoImpl(context: Context): LocalUserInfo {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
    private val editor = sharedPrefs.edit()

    override fun clearInfo() { editor.clear().apply() }

    override fun saveUserInfo(accountIdV4: String, sessionId: String) {
        editor.run {
            putString("account_id_v4", accountIdV4)
            putString("session_id", sessionId)
            apply()
        }
    }

    override fun getLocalSaveUserInfoIfExist() {
        getInfoIfExist { isSaved, accountId, sessionId ->
            if (isSaved) {
                TmdbData.run {
                    this.accountIdV4 = accountId
                    this.sessionId = sessionId
                    //TODO language ISO
                }
            }
        }
    }

    private fun getInfoIfExist(info: (Boolean, String, String) -> Unit) {
        val (accountId, sessionId) = getUserInfo()
        if (accountId.isNotEmpty() || sessionId.isNotEmpty()) {
            info(true, accountId, sessionId)
        } else info(false, accountId, sessionId)
    }

    private fun getUserInfo(): Pair<String, String> {
        val accountId = sharedPrefs.getString("account_id_v4", "noId") as String
        val sessionId = sharedPrefs.getString("session_id", "noSessionId") as String
        return Pair(accountId, sessionId)
    }
}