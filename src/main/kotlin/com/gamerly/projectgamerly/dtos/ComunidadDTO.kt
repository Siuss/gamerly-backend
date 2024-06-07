package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Juego

class ComunidadDTO {
    val foto: String,
    val juego: String,
    val plataforma: String

    fun toJuego(): Juego {
        return Juego(
                nombre = this.juego,
                imagen = this.foto,
                plataformas = listOf(this.plataforma)
        )
    }
}

