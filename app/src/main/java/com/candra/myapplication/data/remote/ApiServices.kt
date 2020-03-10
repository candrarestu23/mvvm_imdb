package com.candra.myapplication.data.remote

import com.candra.myapplication.base.ResponseArray
import com.candra.myapplication.data.model.ListMovieModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("discover/movie")
    fun getListMovie(
        @Query("api_key") apiKey : String,
        @Query("language") language: String,
        @Query("sort_by") sortBy : String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("page") page: Int
    ) : Single<ResponseArray<ListMovieModel>>
}