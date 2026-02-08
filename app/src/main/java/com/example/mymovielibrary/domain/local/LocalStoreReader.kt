package com.example.mymovielibrary.domain.local

interface LocalStoreReader {
    val requestToken: String?
    val accessToken: String?
    val sessionId: String?
    val accountIdV4: String?
    val accountIdV3: Int?
    val iso639: String
    val iso3166: String
}