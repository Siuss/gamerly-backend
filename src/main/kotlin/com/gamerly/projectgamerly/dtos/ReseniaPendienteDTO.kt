package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario

class ReseniaPendienteDTO {
    var id: Long = 0
    lateinit var nombre: String
    lateinit var fileName: String
    lateinit var discord: String

    companion object {
        fun fromResenia(usuarioEmisor: Usuario, resenia: Resenia) : ReseniaPendienteDTO = ReseniaPendienteDTO().also {
            it.id = resenia.id
            it.nombre = usuarioEmisor.nombre
            it.fileName = usuarioEmisor.fileName
            it.discord = usuarioEmisor.discord
        }
    }

}