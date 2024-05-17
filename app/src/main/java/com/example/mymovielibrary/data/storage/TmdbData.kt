package com.example.mymovielibrary.data.storage

object TmdbData {
    var requestToken: String = "noToken"
    var accessToken: String = "noToken"
    var sessionId: String = "noSessionId"
    var accountIdV3: Int = 0
    var accountIdV4: String = "noId"
    var languageIso: String = "en" //by default

    fun TmdbData.clear() {
        requestToken = "noToken"
        accessToken = "noToken"
        sessionId = "noSessionId"
        accountIdV3 = 0
        accountIdV4 = "noId"
        languageIso = "en"
    }
}