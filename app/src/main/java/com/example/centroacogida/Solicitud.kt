package com.example.centroacogida

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes")
data class Solicitud(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,   // el id lo pone la base de datos
    val animalId: Int,
    val animalNombre: String,
    val nombre: String,
    val email: String,
    val telefono: String,
    val fecha: Long = System.currentTimeMillis()
)