package com.example.proyectolumier.data.model

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Movie(
    val id: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val imageResourceId: Int,
    val genre: MovieGenre,
    @StringRes val trailerUrlRes: Int,     // ID del recurso string del trailer
    @StringRes val platformsIdRes: Int,    // ID del recurso string "1,2,3"
    @StringRes val dateRes: Int,           // ID del recurso string año
    @StringRes val durationRes: Int,       // ID del recurso string duración
    @StringRes val ageRes: Int             // ID del recurso string edad
)

enum class MovieGenre {
    Terror, Amor, Accion, Comedia
}