package com.example.poopie1.BD

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.poopie1.Cities.Cities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = arrayOf(Cities::class), version = 4, exportSchema = false)
public abstract class CityBD : RoomDatabase {

    abstract fun cityDao(): CityDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var cityDao = database.cityDao()
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: CityBD? = null

        fun getDatabase( context: Context, scope: CoroutineScope): CityBD {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized (this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityBD::class.java,
                    "cities_database",
                )
                     //Estrategia destruiçao
                    .addCallback(WordDatabaseCallback(scope))
                    .build()

                    INSTANCE = instance
                    return instance
            }
        }
    }

}