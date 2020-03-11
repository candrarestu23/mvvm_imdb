package com.candra.myapplication.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Candra Restu on 11,March,2020
 */
class DetailMovieModel (
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    val genres: List<GenresModel>,
    val id: Int?,

    @SerializedName("original_title")
    val originalTitle: String?,

    val overview: String?,
    val popularity: Double?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseData: String?,

    val runtime: Int?,
    val status: String?,

    @SerializedName("vote_average")
    val voteAvarage: Double?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    val videos: VideoParentModel?
)

class GenresModel(
    val id: Int?,
    val name: Int?
)

class VideoParentModel(
    val results: List<VideosModel>?
)
class VideosModel(
    val id: Int?,
    val key: String?,
    val name: String?
)