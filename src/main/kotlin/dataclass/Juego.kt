package dataclass

import java.util.Date

data class Juego(
    val titulo: String,
    val genero: String?,
    val precio: Double?,
    val fecha_lanzamiento: Date
    )
