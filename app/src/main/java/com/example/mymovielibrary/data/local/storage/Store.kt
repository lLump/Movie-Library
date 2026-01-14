package com.example.mymovielibrary.data.local.storage

object Store {
    var tmdbData: TmdbData = TmdbData()
    var userCollections: List<Pair<Int, String>> = listOf()

    fun TmdbData.clear() {
        requestToken = "noToken"
        accessToken = "noToken"
        sessionId = "noSessionId"
        accountIdV3 = 0
        accountIdV4 = "noId"
        iso639 = "en"
        iso3166 = "US"
    }
}

data class TmdbData(
    var requestToken: String = "noToken",
    var accessToken: String = "noToken",
    var sessionId: String = "noSessionId",
    var accountIdV3: Int = 0,
    var accountIdV4: String = "noId",
    var iso639: String = "en", //by default
    var iso3166: String = "US"
)