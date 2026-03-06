package com.example.proyectolumier.data.local

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.content.Context
import com.example.proyectolumier.R
import com.example.proyectolumier.data.model.Movie
import com.example.proyectolumier.data.model.MovieGenre

object LocalMovieDataProvider {
    val allMovies = listOf(
        Movie(
            id = 1,
            title = R.string.terror_1_name,
            description = R.string.terror_1_desc,
            imageResourceId = R.drawable.hereditary, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_1_trailer,
            platformsIdRes = R.string.terror_1_platforms_ids,
            dateRes = R.string.terror_1_date,
            durationRes = R.string.terror_1_duration,
            ageRes = R.string.terror_1_age

        ),
        Movie(
            id = 2,
            title = R.string.terror_2_name,
            description = R.string.terror_2_desc,
            imageResourceId = R.drawable.the_conjuring, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_2_trailer,
            platformsIdRes = R.string.terror_2_platforms_ids,
            dateRes = R.string.terror_2_date,
            durationRes = R.string.terror_2_duration,
            ageRes = R.string.terror_2_age

        ),
        Movie(
            id = 3,
            title = R.string.terror_3_name,
            description = R.string.terror_3_desc,
            imageResourceId = R.drawable.smile, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_3_trailer,
            platformsIdRes = R.string.terror_3_platforms_ids,
            dateRes = R.string.terror_3_date,
            durationRes = R.string.terror_3_duration,
            ageRes = R.string.terror_3_age

        ),
        Movie(
            id = 4,
            title = R.string.terror_4_name,
            description = R.string.terror_4_desc,
            imageResourceId = R.drawable.it, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_4_trailer,
            platformsIdRes = R.string.terror_4_platforms_ids,
            dateRes = R.string.terror_4_date,
            durationRes = R.string.terror_4_duration,
            ageRes = R.string.terror_4_age

        ),
        Movie(
            id = 5,
            title = R.string.terror_5_name,
            description = R.string.terror_5_desc,
            imageResourceId = R.drawable.the_nun, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_5_trailer,
            platformsIdRes = R.string.terror_5_platforms_ids,
            dateRes = R.string.terror_5_date,
            durationRes = R.string.terror_5_duration,
            ageRes = R.string.terror_5_age

        ),
        Movie(
            id = 6,
            title = R.string.terror_6_name,
            description = R.string.terror_6_desc,
            imageResourceId = R.drawable.insidious, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_6_trailer,
            platformsIdRes = R.string.terror_6_platforms_ids,
            dateRes = R.string.terror_6_date,
            durationRes = R.string.terror_6_duration,
            ageRes = R.string.terror_6_age

        ),
        Movie(
            id = 7,
            title = R.string.terror_7_name,
            description = R.string.terror_7_desc,
            imageResourceId = R.drawable.midsommar, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_7_trailer,
            platformsIdRes = R.string.terror_7_platforms_ids,
            dateRes = R.string.terror_7_date,
            durationRes = R.string.terror_7_duration,
            ageRes = R.string.terror_7_age

        ),
        Movie(
            id = 8,
            title = R.string.terror_8_name,
            description = R.string.terror_8_desc,
            imageResourceId = R.drawable.a_quiet_place, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_8_trailer,
            platformsIdRes = R.string.terror_8_platforms_ids,
            dateRes = R.string.terror_8_date,
            durationRes = R.string.terror_8_duration,
            ageRes = R.string.terror_8_age

        ),
        Movie(
            id = 9,
            title = R.string.terror_9_name,
            description = R.string.terror_9_desc,
            imageResourceId = R.drawable.the_babadook, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_9_trailer,
            platformsIdRes = R.string.terror_9_platforms_ids,
            dateRes = R.string.terror_9_date,
            durationRes = R.string.terror_9_duration,
            ageRes = R.string.terror_9_age

        ),
        Movie(
            id = 10,
            title = R.string.terror_10_name,
            description = R.string.terror_10_desc,
            imageResourceId = R.drawable.get_out, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Terror,
            trailerUrlRes = R.string.terror_10_trailer,
            platformsIdRes = R.string.terror_10_platforms_ids,
            dateRes = R.string.terror_10_date,
            durationRes = R.string.terror_10_duration,
            ageRes = R.string.terror_10_age

        ),
        Movie(
            id = 11,
            title = R.string.romcom_1_name,
            description = R.string.romcom_1_desc,
            imageResourceId = R.drawable.anyone_but_you, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_1_trailer,
            platformsIdRes = R.string.terror_1_platforms_ids,
            dateRes = R.string.romcom_1_date,
            durationRes = R.string.romcom_1_duration,
            ageRes = R.string.romcom_1_age

        ),
        Movie(
            id = 12,
            title = R.string.romcom_2_name,
            description = R.string.romcom_2_desc,
            imageResourceId = R.drawable.crazy_rich_asians, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_2_trailer,
            platformsIdRes = R.string.terror_2_platforms_ids,
            dateRes = R.string.romcom_2_date,
            durationRes = R.string.romcom_2_duration,
            ageRes = R.string.romcom_2_age
        ),
        Movie(
            id = 13,
            title = R.string.romcom_3_name,
            description = R.string.romcom_3_desc,
            imageResourceId = R.drawable.love__simon, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_3_trailer,
            platformsIdRes = R.string.terror_3_platforms_ids,
            dateRes = R.string.romcom_3_date,
            durationRes = R.string.romcom_3_duration,
            ageRes = R.string.romcom_3_age,
        ),
        Movie(
            id = 14,
            title = R.string.romcom_4_name,
            description = R.string.romcom_4_desc,
            imageResourceId = R.drawable.to_all_the_boys_i_ve_loved_before, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_4_trailer,
            platformsIdRes = R.string.terror_4_platforms_ids,
            dateRes = R.string.romcom_4_date,
            durationRes = R.string.romcom_4_duration,
            ageRes = R.string.romcom_4_age,
        ),

        Movie(
            id = 15,
            title = R.string.romcom_5_name,
            description = R.string.romcom_5_desc,
            imageResourceId = R.drawable.the_big_sick, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_5_trailer,
            platformsIdRes = R.string.terror_5_platforms_ids,
            dateRes = R.string.romcom_5_date,
            durationRes = R.string.romcom_5_duration,
            ageRes = R.string.romcom_5_age
        ),

        Movie(
            id = 16,
            title = R.string.romcom_6_name,
            description = R.string.romcom_6_desc,
            imageResourceId = R.drawable.about_time, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_6_trailer,
            platformsIdRes = R.string.terror_6_platforms_ids,
            dateRes = R.string.romcom_6_date,
            durationRes = R.string.romcom_6_duration,
            ageRes = R.string.romcom_6_age,
        ),

        Movie(
            id = 17,
            title = R.string.romcom_7_name,
            description = R.string.romcom_7_desc,
            imageResourceId = R.drawable.la_la_land, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_7_trailer,
            platformsIdRes = R.string.terror_7_platforms_ids,
            dateRes = R.string.romcom_7_date,
            durationRes = R.string.romcom_7_duration,
            ageRes = R.string.romcom_7_age,
        ),

        Movie(
            id = 18,
            title = R.string.romcom_8_name,
            description = R.string.romcom_8_desc,
            imageResourceId = R.drawable.notting_hill, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_8_trailer,
            platformsIdRes = R.string.terror_8_platforms_ids,
            dateRes = R.string.romcom_8_date,
            durationRes = R.string.romcom_8_duration,
            ageRes = R.string.romcom_8_age,
        ),

        Movie(
            id = 19,
            title = R.string.romcom_9_name,
            description = R.string.romcom_9_desc,
            imageResourceId = R.drawable.palm_springs, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_9_trailer,
            platformsIdRes = R.string.terror_9_platforms_ids,
            dateRes = R.string.romcom_9_date,
            durationRes = R.string.romcom_9_duration,
            ageRes = R.string.romcom_9_age,
        ),
        Movie(
            id = 20,
            title = R.string.romcom_10_name,
            description = R.string.romcom_10_desc,
            imageResourceId = R.drawable.the_proposal, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Amor,
            trailerUrlRes = R.string.romcom_10_trailer,
            platformsIdRes = R.string.terror_10_platforms_ids,
            dateRes = R.string.romcom_10_date,
            durationRes = R.string.romcom_10_duration,
            ageRes = R.string.romcom_10_age,
        ),
        Movie(
            id = 21,
            title = R.string.comedy_1_name,
            description = R.string.comedy_1_desc,
            imageResourceId = R.drawable.free_guy, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_1_trailer,
            platformsIdRes = R.string.comedy_1_platforms_ids,
            dateRes = R.string.comedy_1_date,
            durationRes = R.string.comedy_2_duration,
            ageRes = R.string.comedy_1_age

        ),
        Movie(
            id = 22,
            title = R.string.comedy_2_name,
            description = R.string.comedy_2_desc,
            imageResourceId = R.drawable.barbie, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_2_trailer,
            platformsIdRes = R.string.comedy_2_platforms_ids,
            dateRes = R.string.comedy_2_date,
            durationRes = R.string.comedy_2_duration,
            ageRes = R.string.comedy_2_age

        ),
        Movie(
            id = 23,
            title = R.string.comedy_3_name,
            description = R.string.comedy_3_desc,
            imageResourceId = R.drawable.the_hangover, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_3_trailer,
            platformsIdRes = R.string.comedy_3_platforms_ids,
            dateRes = R.string.comedy_3_date,
            durationRes = R.string.comedy_3_duration,
            ageRes = R.string.comedy_3_age

        ),
        Movie(
            id = 24,
            title = R.string.comedy_4_name,
            description = R.string.comedy_4_desc,
            imageResourceId = R.drawable.superbad, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_4_trailer,
            platformsIdRes = R.string.comedy_4_platforms_ids,
            dateRes = R.string.comedy_4_date,
            durationRes = R.string.comedy_4_duration,
            ageRes = R.string.comedy_4_age

        ),
        Movie(
            id = 25,
            title = R.string.comedy_5_name,
            description = R.string.comedy_5_desc,
            imageResourceId = R.drawable.we_are_the_millers, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_5_trailer,
            platformsIdRes = R.string.comedy_5_platforms_ids,
            dateRes = R.string.comedy_5_date,
            durationRes = R.string.comedy_5_duration,
            ageRes = R.string.comedy_5_age

        ),
        Movie(
            id = 26,
            title = R.string.comedy_6_name,
            description = R.string.comedy_6_desc,
            imageResourceId = R.drawable.step_brothers, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_6_trailer,
            platformsIdRes = R.string.comedy_6_platforms_ids,
            dateRes = R.string.comedy_6_date,
            durationRes = R.string.comedy_6_duration,
            ageRes = R.string.comedy_6_age

        ),

        Movie(
            id = 27,
            title = R.string.comedy_7_name,
            description = R.string.comedy_7_desc,
            imageResourceId = R.drawable.yes_man, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_7_trailer,
            platformsIdRes = R.string.comedy_7_platforms_ids,
            dateRes = R.string.comedy_7_date,
            durationRes = R.string.comedy_7_duration,
            ageRes = R.string.comedy_7_age

        ),
        Movie(
            id = 28,
            title = R.string.comedy_8_name,
            description = R.string.comedy_8_desc,
            imageResourceId = R.drawable._1_jump_street, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_8_trailer,
            platformsIdRes = R.string.comedy_8_platforms_ids,
            dateRes = R.string.comedy_8_date,
            durationRes = R.string.comedy_8_duration,
            ageRes = R.string.comedy_8_age

        ),
        Movie(
            id = 29,
            title = R.string.comedy_9_name,
            description = R.string.comedy_9_desc,
            imageResourceId = R.drawable.deadpool, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_9_trailer,
            platformsIdRes = R.string.comedy_9_platforms_ids,
            dateRes = R.string.comedy_9_date,
            durationRes = R.string.comedy_9_duration,
            ageRes = R.string.comedy_9_age

        ),
        Movie(
            id = 30,
            title = R.string.comedy_10_name,
            description = R.string.comedy_10_desc,
            imageResourceId = R.drawable.the_mask, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Comedia,
            trailerUrlRes = R.string.comedy_10_trailer,
            platformsIdRes = R.string.comedy_10_platforms_ids,
            dateRes = R.string.comedy_10_date,
            durationRes = R.string.comedy_10_duration,
            ageRes = R.string.comedy_10_age

        ),
        Movie(
            id = 31,
            title = R.string.action_1_name,
            description = R.string.action_1_desc,
            imageResourceId = R.drawable.john_wick, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_1_trailer,
            platformsIdRes = R.string.action_1_platforms_ids,
            dateRes = R.string.action_1_date,
            durationRes = R.string.action_1_duration,
            ageRes = R.string.action_1_age

        ),
        Movie(
            id = 32,
            title = R.string.action_2_name,
            description = R.string.action_2_desc,
            imageResourceId = R.drawable.mad_max_fury_road, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_2_trailer,
            platformsIdRes = R.string.action_2_platforms_ids,
            dateRes = R.string.action_2_date,
            durationRes = R.string.action_2_duration,
            ageRes = R.string.action_2_age

        ),
        Movie(
            id = 33,
            title = R.string.action_3_name,
            description = R.string.action_3_desc,
            imageResourceId = R.drawable.avengers_endgame, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_3_trailer,
            platformsIdRes = R.string.action_3_platforms_ids,
            dateRes = R.string.action_3_date,
            durationRes = R.string.action_3_duration,
            ageRes = R.string.action_3_age

        ),
        Movie(
            id = 34,
            title = R.string.action_4_name,
            description = R.string.action_4_desc,
            imageResourceId = R.drawable.the_dark_knight, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_4_trailer,
            platformsIdRes = R.string.action_4_platforms_ids,
            dateRes = R.string.action_4_date,
            durationRes = R.string.action_4_duration,
            ageRes = R.string.action_4_age

        ),
        Movie(
            id = 35,
            title = R.string.action_5_name,
            description = R.string.action_5_desc,
            imageResourceId = R.drawable.mission_impossible_fallout, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_5_trailer,
            platformsIdRes = R.string.action_5_platforms_ids,
            dateRes = R.string.action_5_date,
            durationRes = R.string.action_5_duration,
            ageRes = R.string.action_5_age

        ),
        Movie(
            id = 36,
            title = R.string.action_6_name,
            description = R.string.action_6_desc,
            imageResourceId = R.drawable.gladiator, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_6_trailer,
            platformsIdRes = R.string.action_6_platforms_ids,
            dateRes = R.string.action_6_date,
            durationRes = R.string.action_6_duration,
            ageRes = R.string.action_6_age

        ),
        Movie(
            id = 37,
            title = R.string.action_7_name,
            description = R.string.action_7_desc,
            imageResourceId = R.drawable.top_gun_maverick, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_7_trailer,
            platformsIdRes = R.string.action_7_platforms_ids,
            dateRes = R.string.action_7_date,
            durationRes = R.string.action_7_duration,
            ageRes = R.string.action_7_age

        ),
        Movie(
            id = 38,
            title = R.string.action_8_name,
            description = R.string.action_8_desc,
            imageResourceId = R.drawable.the_matrix, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_8_trailer,
            platformsIdRes = R.string.action_8_platforms_ids,
            dateRes = R.string.action_8_date,
            durationRes = R.string.action_8_duration,
            ageRes = R.string.action_8_age

        ),
        Movie(
            id = 39,
            title = R.string.action_9_name,
            description = R.string.action_9_desc,
            imageResourceId = R.drawable.extraction, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_9_trailer,
            platformsIdRes = R.string.action_9_platforms_ids,
            dateRes = R.string.action_9_date,
            durationRes = R.string.action_9_duration,
            ageRes = R.string.action_9_age

        ),
        Movie(
            id = 40,
            title = R.string.action_10_name,
            description = R.string.action_10_desc,
            imageResourceId = R.drawable.diehard, // Asegúrate de que el archivo se llame así
            genre = MovieGenre.Accion,
            trailerUrlRes = R.string.action_10_trailer,
            platformsIdRes = R.string.action_10_platforms_ids,
            dateRes = R.string.action_10_date,
            durationRes = R.string.action_10_duration,
            ageRes = R.string.action_10_age

        )
    )

}