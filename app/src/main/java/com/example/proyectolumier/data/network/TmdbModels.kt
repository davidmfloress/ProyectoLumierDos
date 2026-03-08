package com.example.proyectolumier.data.network

/**
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import com.google.gson.annotations.SerializedName

data class TmdbMovieResponse(
    @SerializedName("results") val results: List<TmdbMovie> = emptyList(),
    @SerializedName("total_results") val totalResults: Int = 0
)

data class TmdbMovie(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("overview") val overview: String = "",
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("genre_ids") val genreIds: List<Int> = emptyList()
) {
    val posterUrl: String
        get() = if (posterPath != null) "https://image.tmdb.org/t/p/w500$posterPath" else ""
}
