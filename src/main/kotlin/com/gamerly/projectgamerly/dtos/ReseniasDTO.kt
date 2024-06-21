package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario

class ReseniasDTO {
    lateinit var nombre: String
    lateinit var foto: String
    var puntaje: Int = 0
    lateinit var comentario: String

    companion object {
        fun fromResenias(usuarioEmisor: Usuario, resenia: Resenia) : ReseniasDTO = ReseniasDTO().also {
            it.nombre = usuarioEmisor.nombre
            it.foto = usuarioEmisor.foto
            it.puntaje = resenia.puntaje
            it.comentario = resenia.comentario
        }
    }

}