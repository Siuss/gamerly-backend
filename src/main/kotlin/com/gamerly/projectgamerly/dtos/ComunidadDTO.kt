package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Juego

class ComunidadDTO {
    lateinit var foto: String
    lateinit var juego: String
    lateinit var plataforma: String


    companion object {
        fun fromComunidad(juego: Juego): ComunidadDTO {
            val comunidadDTO = ComunidadDTO()
            comunidadDTO.foto = "https://www.google.com/search?q=${juego.nombre}+game&tbm=isch"
            comunidadDTO.juego = juego.nombre
            comunidadDTO.plataforma = juego.plataformas.joinToString(", ")
            return comunidadDTO
        }
    }
}

