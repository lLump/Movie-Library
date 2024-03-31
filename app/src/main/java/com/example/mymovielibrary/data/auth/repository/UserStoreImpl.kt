package com.example.mymovielibrary.data.auth.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.mymovielibrary.domain.auth.repository.UserStore
import com.example.mymovielibrary.domain.auth.model.UserInfo
import javax.inject.Singleton

@Singleton
class UserStoreImpl(context: Context): UserStore {
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

    override var requestToken = "noToken"
    override var sessionId = "noSessionId"

    override fun saveUserCredentials(user: UserInfo) {
        editor.run {
            putString("login", user.username)
            putString("password", user.password)
            apply()
        }
    }

    override fun getUserIfSaved(saved: (Pair<Boolean, UserInfo>) -> Unit) {
        val user = getUserCredentials()
        if (user.username.isNotEmpty() || user.password.isNotEmpty()) {
            saved(Pair(true, user))
        } else saved(Pair(false, user))
    }

    //TODO проверить запрос к несуществующим полям и убрать в случае as String
    private fun getUserCredentials(): UserInfo {
        val login = sharedPrefs.getString("login", "") as String
        val password = sharedPrefs.getString("password", "") as String
        return UserInfo(login, password)
    }

    fun clearPrefs() { editor.clear().apply() }
}