package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario

class ReseniasDTO {
    var idResenia: Long = 0
    var idUsuarioEmisor: Long = 0
    lateinit var nombre: String
    lateinit var fileName: String
    var puntaje: Int = 0
    lateinit var comentario: String
    var verificada: Boolean = false

    companion object {
        fun fromResenias(usuarioEmisor: Usuario, resenia: Resenia) : ReseniasDTO = ReseniasDTO().also {
            it.idResenia = resenia.id
            it.idUsuarioEmisor = usuarioEmisor.id
            it.nombre = usuarioEmisor.nombre
            it.fileName = usuarioEmisor.fileName
            it.puntaje = resenia.puntaje
            it.comentario = resenia.comentario
            it.verificada = resenia.verificada
        }
    }

}