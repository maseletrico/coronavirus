package com.maseletrico.coronavirus.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteCountriesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val favoriteCountry1: String,
    val favoriteCountry2: String,
    val favoriteCountry3: String,
    val favoriteCountry4: String,
    val favoriteCountry5: String
) {

}