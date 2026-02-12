package com.example.mymovielibrary.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import com.example.mymovielibrary.domain.local.LocalStoreReader

class LocalInfoManagerImpl(context: Context): LocalStoreWriter, LocalStoreReader {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)

    override val requestToken: String?
        get() = sharedPrefs.getString("request_token", null)

    override val accessToken: String?
        get() = sharedPrefs.getString("access_token", null)
    //мини задачка
//    override val accessToken: String? = null
//        get() = if (field.isNullOrEmpty()) {
//            sharedPrefs.getString("access_token", null)
//        } else field

    override val sessionId: String?
        get() = sharedPrefs.getString("session_id", null)

    override val accountIdV4: String?
        get() = sharedPrefs.getString("account_id_v4", null)

    override val accountIdV3: Int
        get() = sharedPrefs.getInt("account_id_v3", 0)

    override val iso639: String
        get() = sharedPrefs.getString("iso639", "en")!!

    override val iso3166: String
        get() = sharedPrefs.getString("iso3166", "US")!!

    override fun clearInfo() { // when logout
        sharedPrefs.edit { clear().apply() }
    }

    override fun saveNewResponseLanguage(iso639: String) { sharedPrefs.edit { putString("iso639", iso639) } }

    override fun saveTempRequestToken(token: String) { sharedPrefs.edit { putString("request_token", token).apply() } }

    override fun saveAccountIdV3(id: Int) {
        if (accountIdV3 == 0) { // to prevent overriding again and again when userProfile loads
            sharedPrefs.edit { putInt("account_id_v3", id).apply() }
        }
    }

    override fun saveUserInfo(accountIdV4: String, sessionId: String, accessToken: String) {
        sharedPrefs.edit {
            putString("account_id_v4", accountIdV4)
            putString("session_id", sessionId)
            putString("access_token", accessToken)
            apply()
        }
    }
}