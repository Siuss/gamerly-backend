package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Solicitud

class SolicitudAmistadPendienteDTO {
    var idUsuario: Long = 0
    var idSolicitud: Long = 0
    var nombre: String = ""
    var foto: String = ""
    var mensaje: String = ""
    var discord: String = ""

    companion object {
        fun from(solicitud: Solicitud): SolicitudAmistadPendienteDTO = SolicitudAmistadPendienteDTO().also { dto ->
            dto.idSolicitud = solicitud.id
            dto.idUsuario = solicitud.usuarioCreador.id
            dto.nombre = solicitud.usuarioReceptor.nombre
            dto.foto = solicitud.usuarioCreador.foto
            dto.mensaje = solicitud.mensaje
            dto.discord = solicitud.usuarioCreador.discord
        }
    }
}