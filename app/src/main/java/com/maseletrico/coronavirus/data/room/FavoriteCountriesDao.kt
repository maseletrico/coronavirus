package com.maseletrico.coronavirus.data.room

import androidx.room.*
import com.maseletrico.coronavirus.data.entities.FavoriteCountriesEntity

@Dao
interface FavoriteCountriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteCountry(favoriteCountriesEntity: FavoriteCountriesEntity)

    @Update
    fun updateFavoriteCountries(favoriteCountriesEntity: FavoriteCountriesEntity)

    @Delete
    fun deleteFavoriteCountries(favoriteCountriesEntity: FavoriteCountriesEntity)

    @Query("SELECT * FROM FavoriteCountriesEntity ")
    fun getFavoriteCountries(): List<FavoriteCountriesEntity>
}