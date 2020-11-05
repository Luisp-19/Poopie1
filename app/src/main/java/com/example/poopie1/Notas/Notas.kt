package com.example.poopie1.Notas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nota_table")

class Notas(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "nota") val nota: String,
    @ColumnInfo(name = "subnota") val subnota: String
)

