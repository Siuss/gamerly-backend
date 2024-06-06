package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario

class ReseniasDTO {
    lateinit var nombre: String
    lateinit var foto: String
    var puntaje: Int = 0
    lateinit var comentario: String

    companion object {
        fun fromResenias(user : Usuario, resenia :Resenia) : ReseniasDTO = ReseniasDTO().also {
            it.nombre = user.nombre
            it.foto = user.foto
            it.puntaje = resenia.puntaje
            it.comentario = resenia.comentario
        }
    }

}