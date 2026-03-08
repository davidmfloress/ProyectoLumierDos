package com.example.proyectolumier.data.model

/**
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Movie(
    val id: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val imageResourceId: Int,
    val genre: MovieGenre,
    @StringRes val trailerUrlRes: Int,
    @StringRes val platformsIdRes: Int,
    @StringRes val dateRes: Int,
    @StringRes val durationRes: Int,
    @StringRes val ageRes: Int
)

enum class MovieGenre {
    Terror, Amor, Accion, Comedia
}