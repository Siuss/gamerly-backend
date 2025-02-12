package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario

class UsuarioMensajeDTO(
    var id: Long = 0,
    var nombre: String,
    var fileName: String
) {
    companion object {
        fun from(usuario: Usuario): UsuarioMensajeDTO = UsuarioMensajeDTO(
            id = usuario.id,
            nombre = usuario.nombre,
            fileName = usuario.fileName
        )
    }
}