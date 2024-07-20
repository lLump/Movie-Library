package com.example.mymovielibrary.domain.image

sealed interface ImageSize {
    val size: String
    val url: String
        get() = "https://image.tmdb.org/t/p/$size"
}

enum class BackdropSize(override val size: String): ImageSize {
    W300("w300"),
    W780("w780"),
    W1280("w1280"),
    ORIGINAL("original")
}

enum class LogoSize(override val size: String): ImageSize {
    W45("w45"),
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W300("w300"),
    W500("w500"),
    ORIGINAL("original")
}

enum class PosterSize(override val size: String): ImageSize {
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W342("w342"),
    W500("w500"),
    W780("w780"),
    ORIGINAL("original")
}

enum class ProfileSize(override val size: String): ImageSize {
    W45("w45"),
    W185("w185"),
    H632("h632"),
    ORIGINAL("original")
}

enum class StillSize(override val size: String): ImageSize {
    W92("w92"),
    W185("w185"),
    W300("w300"),
    ORIGINAL("original")
}