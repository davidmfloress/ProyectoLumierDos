package com.example.proyectolumier.ui

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.proyectolumier.data.datastore.LumierPreferencesRepository
import com.example.proyectolumier.data.db.FavoritoEntity
import com.example.proyectolumier.data.db.LumierDatabase
import com.example.proyectolumier.data.local.LocalMovieDataProvider
import com.example.proyectolumier.data.model.Movie
import com.example.proyectolumier.data.model.MovieGenre
import com.example.proyectolumier.data.repository.FavoritosRepository
import com.google.firebase.auth.FirebaseAuth

class CineViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(CineUiState())
    val uiState: StateFlow<CineUiState> = _uiState.asStateFlow()

    // Room
    private val db = LumierDatabase.getDatabase(application)
    private val favRepo = FavoritosRepository(db.favoritoDao())

    // DataStore — persistencia de preferencias (Unidad 6 Pathway 3)
    private val prefsRepo = LumierPreferencesRepository(application)

    // Favoritos reactivos
    private val _favoritosFlow = MutableStateFlow<List<FavoritoEntity>>(emptyList())
    val favoritos: StateFlow<List<FavoritoEntity>> = _favoritosFlow.asStateFlow()

    init {
        updateCurrentGenre(MovieGenre.Terror)

        // Cargar modo oscuro desde DataStore al iniciar
        viewModelScope.launch {
            prefsRepo.darkModeFlow.collect { darkMode ->
                _uiState.update { it.copy(isDarkMode = darkMode) }
            }
        }

        // Comprobar sesión Firebase activa
        FirebaseAuth.getInstance().currentUser?.let { user ->
            setUsuarioLogado(user.email ?: "")
        }
    }

    // ─── Auth ────────────────────────────────────────────────────────────────

    fun setUsuarioLogado(email: String) {
        _uiState.update { it.copy(usuarioEmail = email, isLoggedIn = true) }
        cargarFavoritos(email)
        // Guardar último usuario en DataStore
        viewModelScope.launch { prefsRepo.saveLastUser(email) }
    }

    fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
        _uiState.update { it.copy(usuarioEmail = null, isLoggedIn = false) }
        _favoritosFlow.value = emptyList()
    }

    // ─── Favoritos (Room) ────────────────────────────────────────────────────

    private fun cargarFavoritos(email: String) {
        viewModelScope.launch {
            favRepo.getFavoritos(email).collect { lista ->
                _favoritosFlow.value = lista
            }
        }
    }

    fun toggleFavorito(movie: Movie, context: Context) {
        val email = _uiState.value.usuarioEmail ?: return
        viewModelScope.launch {
            favRepo.toggleFavorito(
                FavoritoEntity(
                    movieId = movie.id,
                    userEmail = email,
                    titulo = context.getString(movie.title),
                    genero = movie.genre.name
                )
            )
        }
    }

    fun eliminarFavorito(fav: FavoritoEntity) {
        viewModelScope.launch { favRepo.toggleFavorito(fav) }
    }

    fun esFavorito(movieId: Int): Boolean =
        _favoritosFlow.value.any { it.movieId == movieId }

    // ─── Tema — guardado en DataStore (Unidad 6 Pathway 3) ──────────────────

    fun updateTheme(isDark: Boolean?) {
        _uiState.update { it.copy(isDarkMode = isDark) }
        viewModelScope.launch { prefsRepo.saveDarkMode(isDark) }
    }

    // ─── Películas ───────────────────────────────────────────────────────────

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

    fun onSearchTextChange(text: String, context: Context) {
        _uiState.update { it.copy(searchText = text) }
        val genre = _uiState.value.currentGenre
        val filteredByGenre = LocalMovieDataProvider.allMovies.filter { it.genre == genre }
        val filteredBySearch = if (text.isEmpty()) filteredByGenre
        else filteredByGenre.filter { movie ->
            context.getString(movie.title).contains(text, ignoreCase = true)
        }
        _uiState.update { it.copy(moviesOfSelectedGenre = filteredBySearch) }
    }

    fun updateAgeFilter(age: String) {
        _uiState.update { it.copy(selectedAgeFilter = age) }
    }
}
