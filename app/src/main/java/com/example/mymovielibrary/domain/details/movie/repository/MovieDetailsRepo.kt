package com.example.mymovielibrary.domain.details.movie.repository

import com.example.mymovielibrary.domain.details.movie.model.MovieDetails
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

interface MovieDetailsRepo {
    suspend fun getMovieDetails(id: Int): Result<MovieDetails, DataError>
//    suspend fun getTvShowDetails(id: Int): Result<>
}