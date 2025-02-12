package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario

class UsuarioBloqueadoDTO {
    var id : Long = 0
    lateinit var nombre: String
    lateinit var fileName: String
    lateinit var email: String

    companion object {
        fun from(usuario: Usuario): UsuarioBloqueadoDTO = UsuarioBloqueadoDTO().also { dto ->
            dto.id = usuario.id
            dto.nombre = usuario.nombre
            dto.fileName = usuario.fileName
            dto.email = usuario.email
        }
    }
}