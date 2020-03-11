package com.candra.myapplication.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.candra.myapplication.data.local.dao.ListMovieDao
import com.candra.myapplication.data.model.ListMovieModel

@Database(entities = [ListMovieModel::class], version = 1, exportSchema = false)
abstract class ListMovieDatabase: RoomDatabase() {
    abstract fun listMovieDao(): ListMovieDao

    companion object {
        @Volatile private var instance: ListMovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ListMovieDatabase::class.java,
            "movilelist"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}