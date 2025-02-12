package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Solicitud

class SolicitudAmistadPendienteDTO {
    var idUsuario: Long = 0
    var idSolicitud: Long = 0
    var nombre: String = ""
    var fileName: String = ""
    var mensaje: String = ""
    var discord: String = ""

    companion object {
        fun from(solicitud: Solicitud): SolicitudAmistadPendienteDTO = SolicitudAmistadPendienteDTO().also { dto ->
            dto.idSolicitud = solicitud.id
            dto.idUsuario = solicitud.usuarioCreador.id
            dto.nombre = solicitud.usuarioCreador.nombre
            dto.fileName = solicitud.usuarioCreador.fileName
            dto.mensaje = solicitud.mensaje
            dto.discord = solicitud.usuarioCreador.discord
        }
    }
}