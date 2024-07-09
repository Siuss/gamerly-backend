package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Mensaje

class MensajeDTO (
    var id: Long,
    var idCreador: Long,
    var idReceptor: Long,
    var contenido: String
){
    companion object {
        fun from(mensaje: Mensaje): MensajeDTO = MensajeDTO(
            id = mensaje.id,
            idCreador = mensaje.usuarioCreador.id,
            idReceptor = mensaje.usuarioReceptor.id,
            contenido = mensaje.contenido
        )
    }
}

