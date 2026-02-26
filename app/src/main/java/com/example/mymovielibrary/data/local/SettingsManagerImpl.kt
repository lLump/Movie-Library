package com.example.mymovielibrary.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.UserCollectionInfo
import com.example.mymovielibrary.domain.local.SettingsReader
import com.example.mymovielibrary.domain.local.SettingsWriter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsManagerImpl(context: Context): SettingsWriter, SettingsReader {
    private val settingsPrefs: SharedPreferences = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
    private val json = Json

    override val language: LanguageDetails
        get() {
            val iso = settingsPrefs.getString("iso639", "en")!!
            val name = getNameByIso(iso)
            return LanguageDetails(name, iso)
        }


    override val userCollections: List<UserCollectionInfo>
        get() {
            val raw = settingsPrefs.getString("collections", "[]")!!
            return try {
                json.decodeFromString(raw)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }

    override val userCollectionsForStats: List<Int>
        get() {
            val raw = settingsPrefs.getString("collections_for_stats", "[]")!!
            return try {
                json.decodeFromString(raw)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }


    override fun clearInfo() {
        settingsPrefs.edit { clear() }
    }

    override fun saveApiResponseLanguage(languageName: String) {
        val iso = getIsoByName(languageName)
        settingsPrefs.edit { putString("iso639", iso) }
    }


    override fun saveUserCollections(collectionsInfo: List<UserCollectionInfo>) {
        val raw = json.encodeToString(collectionsInfo)
        settingsPrefs.edit { putString("collections", raw) }
    }

    override fun saveUserCollectionForStats(collectionInfo: UserCollectionInfo) {
        val currentCollections = userCollectionsForStats.toMutableList()
        currentCollections.add(collectionInfo.id)
        val raw = json.encodeToString(currentCollections.toList())
        settingsPrefs.edit { putString("collections_for_stats", raw) }
    }

    override fun removeUserCollectionFromStats(collectionInfo: UserCollectionInfo) {
        val currentCollections = userCollectionsForStats.toMutableList()
        currentCollections.remove(collectionInfo.id)
        val raw = json.encodeToString(currentCollections.toList())
        settingsPrefs.edit { putString("collections_for_stats", raw) }
    }

    private fun getNameByIso(iso: String): String {
        return getLanguages().find { it.iso639 == iso }!!.name
    }

    private fun getIsoByName(name: String): String {
        return getLanguages().find { it.name == name }!!.iso639
    }

    private fun getLanguages(): List<LanguageDetails> { // im too lazy to get that from the server fixme
        return listOf(
            LanguageDetails(name = "English", iso639 = "en"),
            LanguageDetails(name = "Русский", iso639 = "ru"),
            LanguageDetails(name = "Українська", iso639 = "uk"),
            LanguageDetails(name = "Deutsch", iso639 = "de"),
            LanguageDetails(name = "Français", iso639 = "fr"),
            LanguageDetails(name = "Český", iso639 = "cs")
        )
    }
}