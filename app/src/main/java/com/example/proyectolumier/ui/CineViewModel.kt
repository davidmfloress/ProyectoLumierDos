package com.example.proyectolumier.ui

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.proyectolumier.data.local.LocalMovieDataProvider
import com.example.proyectolumier.data.model.Movie
import com.example.proyectolumier.data.model.MovieGenre

class CineViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CineUiState())
    val uiState: StateFlow<CineUiState> = _uiState.asStateFlow()

    init {
        updateCurrentGenre(MovieGenre.Terror)
    }

    fun updateCurrentGenre(genre: MovieGenre) {
        val filteredMovies = LocalMovieDataProvider.allMovies.filter { it.genre == genre }
        _uiState.update { currentState ->
            currentState.copy(
                currentGenre = genre,
                

                moviesOfSelectedGenre = filteredMovies,
                selectedMovie = filteredMovies.firstOrNull() ?: currentState.selectedMovie,
                isShowingListPage = true
            )
        }
    }


    fun updateOrder(order: MovieOrder, context: Context) {
        _uiState.update { currentState ->
            currentState.copy(
                currentOrder = order,
                moviesOfSelectedGenre = sortMovies(currentState.moviesOfSelectedGenre, order, context)
            )
        }
    }


    private fun sortMovies(movies: List<Movie>, order: MovieOrder, context: Context): List<Movie> {
        return when (order) {
            MovieOrder.AZ -> movies.sortedBy { context.getString(it.title) }
            MovieOrder.YearNewest -> movies.sortedByDescending { context.getString(it.dateRes) }
        }
    }

    fun updateDetailsScreenState(movie: Movie) {
        _uiState.update { it.copy(selectedMovie = movie, isShowingListPage = false) }
    }

    fun updateTheme(isDark: Boolean?) {
        _uiState.update { currentState ->
            currentState.copy(isDarkMode = isDark)
        }
    }

    fun onSearchTextChange(text: String, context: android.content.Context) {
        _uiState.update { it.copy(searchText = text) }

        val genre = _uiState.value.currentGenre

        val filteredByGenre = LocalMovieDataProvider.allMovies.filter { it.genre == genre }

        val filteredBySearch = if (text.isEmpty()) {
            filteredByGenre
        } else {
            filteredByGenre.filter { movie ->
                context.getString(movie.title).contains(text, ignoreCase = true)
            }
        }

        _uiState.update { it.copy(moviesOfSelectedGenre = filteredBySearch) }
    }

    fun updateAgeFilter(age: String) {
        _uiState.update { it.copy(selectedAgeFilter = age) }


    }
}