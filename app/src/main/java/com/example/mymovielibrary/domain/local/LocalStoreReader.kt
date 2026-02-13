package com.example.mymovielibrary.domain.local

interface LocalStoreReader {
    val tempToken: String?
    val accessToken: String?
    val sessionId: String?
    val accountIdV4: String?
    val accountIdV3: Int?
    val iso639: String
    val iso3166: String
}

/*
  В запросах достаточно 639, без формата 639-3166 *работает по милости сервера*
    English 639 'en' 3166 'ENG'
    Russian 639 'ru' 3166 'RU'
    Ukrainian 639 'uk' 3166 'UA'
    German 639 'de' 3166 'DE'
    French 639 'fr' 3166 'FR'
    Czech 639 'cs' 3166 'CZ'
 */
