package com.example.mymovielibrary.data.remote.details.movie.repository

import com.example.mymovielibrary.data.remote.base.repository.BaseRepository
import com.example.mymovielibrary.data.remote.details.movie.api.MovieDetailsApi
import com.example.mymovielibrary.data.remote.details.movie.model.toMovieDetails
import com.example.mymovielibrary.domain.details.movie.model.MovieDetails
import com.example.mymovielibrary.domain.details.movie.repository.MovieDetailsRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

class MovieDetailsRepoImpl(private val api: MovieDetailsApi, localStore: LocalStoreReader): MovieDetailsRepo, BaseRepository(localStore) {
    override suspend fun getMovieDetails(id: Int): Result<MovieDetails, DataError> {
        return safeApiCall("Getting MovieDetails failed") {
            val response = api.getMovieDetails(id)
            response.toMovieDetails()
        }
    }
}