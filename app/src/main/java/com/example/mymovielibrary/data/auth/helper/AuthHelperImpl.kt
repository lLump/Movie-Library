package com.example.mymovielibrary.data.auth.helper

import com.example.mymovielibrary.data.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.data.auth.repository.UserTmdbInfoImpl
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.base.helper.BaseHelper

class AuthHelperImpl(
    private val authRepo: AuthRepoImpl,
    private val userInfo: UserTmdbInfoImpl,
) : AuthHelper, BaseHelper() {

//    private fun guestLogin() {
//        scope.launch(Dispatchers.IO) {
//            TmdbData.sessionId = //getting & saving guestSessionId
//                request { authRepo.getGuestSessionId() } ?: "noSessionId"
//        }
//    }

    override suspend fun logout() {
        authRepo.logout()
        userInfo.clearInfo()
    }

    override suspend fun getRequestToken() = request { authRepo.createRequestTokenV4() } ?: "noToken"

    override suspend fun finishAuth(requestToken: String) {
        val (accountId, token) = request { authRepo.createAccessTokenV4(requestToken) }
            ?: Pair("noId", "noToken")
        val sessionId = request { authRepo.getSessionIdV4(token) } ?: "noSessionId"
        TmdbData.run {
            this.accountIdV4 = accountId
            this.accessToken = token
            this.sessionId = sessionId
        }
        userInfo.saveUserInfo(accountId, sessionId, token) //local save into prefs
    }
}