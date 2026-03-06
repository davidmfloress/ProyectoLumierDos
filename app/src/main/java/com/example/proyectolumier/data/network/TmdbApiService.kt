package com.example.proyectolumier.data.network

/**
 * Interfaz Retrofit para llamadas a la API de TMDB.
 * Unidad 5 - Pathway 1: API REST con Retrofit + Corrutinas
 *
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// API Key pública de TMDB (demo key) ya no es demo
private const val TMDB_API_KEY = "a924644d43b41d998ff5a45d79d350d3"
private const val BASE_URL = "https://api.themoviedb.org/3/"

interface TmdbApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): TmdbMovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("query") query: String,
        @Query("language") language: String = "es-ES"
    ): TmdbMovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = "es-ES"
    ): TmdbMovieResponse
}

// Singleton de Retrofit
object TmdbApi {
    val retrofitService: TmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }
}
