package com.example.poopie1.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.poopie1.Notas.Notas

@Dao
interface DaoSan{

        @Query("SELECT * from nota_table ORDER BY nota ASC")
        fun getAlphabetizedCities(): LiveData<List<Notas>>

        /* @Query("SELECT * from city_table WHERE country == :name")
         fun getCitiesFromCountry(name: String): LiveData<List> */

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(notas: Notas)

        @Delete
        suspend fun delete(notas: Notas )

        @Query("DELETE FROM nota_table")
        suspend fun deleteAll()

}