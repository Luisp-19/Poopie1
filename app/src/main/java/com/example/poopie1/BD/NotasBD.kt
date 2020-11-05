package com.example.poopie1.BD

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.poopie1.Notas.Notas
import com.example.poopie1.dao.DaoSan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Notas::class], version = 1, exportSchema = false)
public abstract class NotasBD : RoomDatabase() {

    abstract fun daoSan(): DaoSan

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var daoSan = database.daoSan()
                }
            }
        }
    }

    companion object {

        // Previne que varios instantes sejam abertos na databse ao mesmo tempo
        @Volatile
        private var INSTANCE: NotasBD? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NotasBD {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized (this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotasBD::class.java,
                    "nota_database",
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