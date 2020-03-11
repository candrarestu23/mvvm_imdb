package com.candra.myapplication.data.remote

import com.candra.myapplication.base.ResponseArray
import com.candra.myapplication.data.model.DetailMovieModel
import com.candra.myapplication.data.model.ListMovieModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{id}")
    fun getDetailMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey : String,
        @Query("language") language: String,
        @Query("append_to_response") append: String
    ) : Single<DetailMovieModel>
}