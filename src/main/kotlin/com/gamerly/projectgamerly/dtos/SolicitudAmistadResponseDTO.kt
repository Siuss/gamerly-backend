package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Solicitud

    class SolicitudAmistadResponseDTO {
        var idCreador: Long = 0
        var idAmigo: Long = 0
        var mensaje: String = ""

        companion object {
            fun from(solicitud: Solicitud): SolicitudAmistadResponseDTO = SolicitudAmistadResponseDTO().also { dto ->
                dto.idCreador = solicitud.usuarioCreador.id
                dto.idAmigo = solicitud.usuarioReceptor.id
                dto.mensaje = solicitud.mensaje
            }
        }
    }