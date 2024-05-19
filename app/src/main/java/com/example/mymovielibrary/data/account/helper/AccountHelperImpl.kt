package com.example.mymovielibrary.data.account.helper

import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.domain.base.helper.BaseHelper
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileDisplay
import com.example.mymovielibrary.presentation.ui.profile.state.UserStats

class AccountHelperImpl(
    private val accConfig: AccountRepository,
) : AccountHelper, BaseHelper() {

    override suspend fun loadLanguages(): List<LanguageDetails> {
        val list = request { accConfig.getLanguages() }
        return if (!list.isNullOrEmpty()) {
            list
        } else  {
            emptyList()
        }
    }

    override suspend fun loadProfileData(): ProfileDisplay? {
            val profileDetails = request { accConfig.getProfileDetails(TmdbData.sessionId) }
            if (profileDetails == null) {
                return null //request error
            } else {
                val displayProfile = ProfileDisplay(
                    avatarPath = profileDetails.avatarPath ?: "2Fj7wrz6ikBMZXx6NBwjDMH3JpHWh.jpg", //default photo path
                    username = profileDetails.username,
//                    name = profileDetails.name,
                    stats = UserStats(), //TODO USER STATS
                    languageIso = profileDetails.languageIso,
                )
                TmdbData.accountIdV3 = profileDetails.id
                return displayProfile
            }
    }
}