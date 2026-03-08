package com.example.proyectolumier.data.repository

/**
 *
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import com.example.proyectolumier.data.network.TmdbApi
import com.example.proyectolumier.data.network.TmdbMovie

// Sealed class de estados de red - Unidad 5 Pathway 1
sealed interface NetworkUiState {
    data class Success(val movies: List<TmdbMovie>) : NetworkUiState
    data class Error(val message: String) : NetworkUiState
    object Loading : NetworkUiState
}

class TmdbRepository {

    suspend fun getPopularMovies(): NetworkUiState {
        return try {
            val response = TmdbApi.retrofitService.getPopularMovies()
            NetworkUiState.Success(response.results)
        } catch (e: Exception) {
            NetworkUiState.Error(e.message ?: "Error de conexión")
        }
    }

    suspend fun getTopRatedMovies(): NetworkUiState {
        return try {
            val response = TmdbApi.retrofitService.getTopRatedMovies()
            NetworkUiState.Success(response.results)
        } catch (e: Exception) {
            NetworkUiState.Error(e.message ?: "Error de conexión")
        }
    }

    suspend fun searchMovies(query: String): NetworkUiState {
        return try {
            val response = TmdbApi.retrofitService.searchMovies(query = query)
            NetworkUiState.Success(response.results)
        } catch (e: Exception) {
            NetworkUiState.Error(e.message ?: "Error de conexión")
        }
    }
}
