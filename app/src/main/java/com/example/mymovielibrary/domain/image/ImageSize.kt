package com.example.mymovielibrary.domain.image

sealed interface ImageSize {
    val size: String
    val url: String
        get() = "https://image.tmdb.org/t/p/$size"
}

enum class BackdropSize(override val size: String): ImageSize {
    W300("w300"),  //очень низкое / низкое
    W780("w780"),  //среднее
    W1280("w1280"), // хорошее
    ORIGINAL("original") //очень хорошее
}

enum class LogoSize(override val size: String): ImageSize {
    W45("w45"),    //очень низкое
    W92("w92"),    //низкое
    W154("w154"),  //ниже среднего
    W185("w185"),  //среднее
    W300("w300"),  //выше среднего
    W500("w500"),  //хорошее
    ORIGINAL("original") //очень хорошее
}

enum class PosterSize(override val size: String): ImageSize {
    W92("w92"),    //очень низкое
    W154("w154"),  //низкое
    W185("w185"),  //ниже среднего
    W342("w342"),  //среднее
    W500("w500"),  //выше среднего
    W780("w780"),  //хорошее
    ORIGINAL("original") //очень хорошее
}

enum class ProfileSize(override val size: String): ImageSize {
    W45("w45"),    //очень низкое / низкое
    W185("w185"),  //среднее / ниже среднего
    H632("h632"),  //хорошее / выше среднего todo 'h'???
    ORIGINAL("original") //очень хорошее
}

enum class StillSize(override val size: String): ImageSize {
    W92("w92"),    //очень низкое / низкое
    W185("w185"),  //среднее / ниже среднего
    W300("w300"),  //хорошее / выше среднего
    ORIGINAL("original") //очень хорошее
}