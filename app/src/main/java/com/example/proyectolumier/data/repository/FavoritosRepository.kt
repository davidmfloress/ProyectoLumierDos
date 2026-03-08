package com.example.proyectolumier.data.repository

/**
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import com.example.proyectolumier.data.db.FavoritoDao
import com.example.proyectolumier.data.db.FavoritoEntity
import kotlinx.coroutines.flow.Flow

class FavoritosRepository(private val dao: FavoritoDao) {

    fun getFavoritos(email: String): Flow<List<FavoritoEntity>> =
        dao.getFavoritosPorUsuario(email)

    suspend fun toggleFavorito(favorito: FavoritoEntity) {
        val existe = dao.getFavoritoPorId(favorito.movieId, favorito.userEmail)
        if (existe != null) {
            dao.eliminarFavorito(existe)
        } else {
            dao.insertarFavorito(favorito)
        }
    }

    suspend fun esFavorito(movieId: Int, email: String): Boolean =
        dao.getFavoritoPorId(movieId, email) != null
}
