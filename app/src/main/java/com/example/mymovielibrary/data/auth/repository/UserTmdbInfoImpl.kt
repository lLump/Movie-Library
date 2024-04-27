package com.example.mymovielibrary.data.auth.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.mymovielibrary.domain.auth.repository.LocalUserInfo

class UserTmdbInfoImpl(context: Context): LocalUserInfo {
    private val key = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val sharedPrefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "encrypted_prefs",
        key,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val editor = sharedPrefs.edit()

    override fun clearInfo() { editor.clear().apply() }

    override fun saveUserInfo(accountIdV4: String, sessionId: String) {
        editor.run {
            putString("account_id_v4", accountIdV4)
            putString("session_id", sessionId)
            apply()
        }
    }

    override fun getInfoIfExist(info: (Boolean, String, String) -> Unit) {
        val (accountId, sessionId) = getUserInfo()
        if (accountId.isNotEmpty() || sessionId.isNotEmpty()) {
            info(true, accountId, sessionId)
        } else info(false, accountId, sessionId)
    }

    //TODO проверить запрос к несуществующим полям и убрать в случае as String
    private fun getUserInfo(): Pair<String, String> {
        val accountId = sharedPrefs.getString("account_id_v4", "") as String
        val sessionId = sharedPrefs.getString("session_id", "") as String
        return Pair(accountId, sessionId)
    }
}