package com.example.centroacogida

// AcogidaUiState.kt
data class AcogidaUiState(
    val animales: List<Animal> = DatosAnimales.animales,
    val soloUrgentes: Boolean = false,
    val textoBusqueda: String = "",
    // TODO Paso 10: el texto de búsqueda linea anterior
    // TODO Paso 11.5 estado y viewModel
    val cargando: Boolean = false,
    val aviso: String? = null
) {
    val animalesVisibles: List<Animal>
        get() = animales
            .filter { !soloUrgentes || it.urgente }
            .filter { it.nombre.contains(textoBusqueda.trim(), ignoreCase = true) }
}