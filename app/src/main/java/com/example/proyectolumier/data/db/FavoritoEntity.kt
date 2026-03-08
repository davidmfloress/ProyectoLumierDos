package com.example.proyectolumier.data.db

/**
 *
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class FavoritoEntity(
    @PrimaryKey val movieId: Int,
    val userEmail: String,
    val titulo: String,
    val genero: String,
    val fechaAgregado: Long = System.currentTimeMillis()
)
