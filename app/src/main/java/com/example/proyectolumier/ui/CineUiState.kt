package com.example.proyectolumier.ui

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import com.example.proyectolumier.data.model.Movie
import com.example.proyectolumier.data.model.MovieGenre
import com.example.proyectolumier.data.local.LocalMovieDataProvider

enum class MovieOrder {
    AZ, YearNewest
}

data class CineUiState(
    val categoryList: List<MovieGenre> = MovieGenre.values().toList(),
    val currentGenre: MovieGenre = MovieGenre.Terror,
    val moviesOfSelectedGenre: List<Movie> = emptyList(),
    val selectedMovie: Movie = LocalMovieDataProvider.allMovies.first(),
    val isShowingListPage: Boolean = true,
    val isDarkMode: Boolean? = null,
    // filtros
    val currentOrder: MovieOrder = MovieOrder.AZ,
    val searchText: String = "",
    val selectedAgeFilter: String = "Todas",
    // auth
    val usuarioEmail: String? = null,
    val isLoggedIn: Boolean = false,
    // geolocalización — persiste durante la sesión aunque se rote la pantalla
    val geoLatitud: Double? = null,
    val geoLongitud: Double? = null,
)
// Nota: añadir estos campos al data class CineUiState existente
