package com.example.poopie1.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.poopie1.Notas.Notas

@Dao
interface DaoSan{

        @Query("SELECT * from nota_table ORDER BY nota ASC")
        fun getAlphabetizedCities(): LiveData<List<Notas>>

        @Update
        suspend fun update(notas: Notas)

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(notas: Notas)

        @Delete
        suspend fun delete(notas: Notas )

        @Query("DELETE FROM nota_table")
        suspend fun deleteAll()

}