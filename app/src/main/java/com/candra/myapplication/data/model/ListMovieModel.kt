package com.candra.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class ListMovieModel (
    @ColumnInfo(name ="popularity")
    @SerializedName("popularity")
    val popularity: Double?,

    @ColumnInfo(name ="vote_count")
    @SerializedName("vote_count")
    val voteCount: Int?,

    @ColumnInfo(name ="video")
    @SerializedName("video")
    val video: Boolean?,

    @ColumnInfo(name ="poster_path")
    @SerializedName("poster_path")
    val posterPath: String?,

    @ColumnInfo(name ="id")
    @SerializedName("id")
    val ID: Int?,

    @ColumnInfo(name ="adult")
    @SerializedName("adult")
    val adult: Boolean?,

    @ColumnInfo(name ="backdrop_path")
    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @ColumnInfo(name ="original_language")
    @SerializedName("original_language")
    val originalLanguage: String?,

    @ColumnInfo(name ="original_title")
    @SerializedName("original_title")
    val originalTitle: String?,

    @ColumnInfo(name ="title")
    @SerializedName("title")
    val title: String?,

    @ColumnInfo(name ="vote_average")
    @SerializedName("vote_average")
    val voteAvarage: String?,

    @ColumnInfo(name ="overview")
    @SerializedName("overview")
    val overview: String?,

    @ColumnInfo(name ="release_date")
    @SerializedName("release_date")
    val releaseDate: String?
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}