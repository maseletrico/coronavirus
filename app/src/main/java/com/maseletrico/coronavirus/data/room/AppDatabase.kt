package com.maseletrico.coronavirus.data.room

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.maseletrico.coronavirus.data.entities.FavoriteCountriesEntity
import kotlin.coroutines.coroutineContext


private val LifecycleOwner.applicationContext: Context
    get() {return applicationContext}

@Database(entities = [FavoriteCountriesEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCountriesDao(): FavoriteCountriesDao

    companion object {
        var INSTANCE: AppDatabase? = null
        fun getAppDatabase(context: LifecycleOwner): AppDatabase? {
            if (INSTANCE != null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "CountriesDb"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase(){
            INSTANCE = null
        }
    }

}