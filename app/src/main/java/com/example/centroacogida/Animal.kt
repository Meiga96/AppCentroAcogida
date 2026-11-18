// Animal.kt
package com.example.centroacogida

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import androidx.annotation.DrawableRes

@Serializable
data class Animal(
    val id: Int,
    val nombre: String,
    val especie: String,
    val edad: Int,
    @Transient @DrawableRes val imagen: Int = R.drawable.ic_launcher_foreground,
    val urgente: Boolean,
    val descripcion: String = ""
)
