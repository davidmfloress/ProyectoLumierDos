package com.example.proyectolumier.data.db

/**
 * Entidad Room que representa una película marcada como favorita por el usuario.
 * Se almacena en la base de datos local del dispositivo.
 *
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class FavoritoEntity(
    @PrimaryKey val movieId: Int,
    val userEmail: String,   // para separar favoritos por usuario Firebase
    val titulo: String,
    val genero: String,
    val fechaAgregado: Long = System.currentTimeMillis()
)
