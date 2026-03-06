package com.example.proyectolumier.data.db

/**
 * DAO (Data Access Object) para operaciones CRUD sobre la tabla de favoritos.
 *
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarFavorito(favorito: FavoritoEntity)

    @Delete
    suspend fun eliminarFavorito(favorito: FavoritoEntity)

    @Query("SELECT * FROM favoritos WHERE userEmail = :email ORDER BY fechaAgregado DESC")
    fun getFavoritosPorUsuario(email: String): Flow<List<FavoritoEntity>>

    @Query("SELECT * FROM favoritos WHERE movieId = :movieId AND userEmail = :email LIMIT 1")
    suspend fun getFavoritoPorId(movieId: Int, email: String): FavoritoEntity?

    @Query("DELETE FROM favoritos WHERE userEmail = :email")
    suspend fun eliminarTodosFavoritos(email: String)
}
