package com.example.proyectolumier.data.db

/**.
 *
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoritoEntity::class], version = 1, exportSchema = false)
abstract class LumierDatabase : RoomDatabase() {

    abstract fun favoritoDao(): FavoritoDao

    companion object {
        @Volatile
        private var INSTANCE: LumierDatabase? = null

        fun getDatabase(context: Context): LumierDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LumierDatabase::class.java,
                    "lumier_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
