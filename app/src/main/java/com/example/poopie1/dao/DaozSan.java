package com.example.poopie1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.poopie1.Cities.Cities;

@Dao
interface DaozSan {

    @Query("SELECT * from city_table ORDER BY city ASC")
    fun getAlphabetizedCities(): LiveData<List<City>>

    @Insert(OnConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: Cities)

    @Query("DELETE FROM city_table")
    suspend fun deleteAll()
}
