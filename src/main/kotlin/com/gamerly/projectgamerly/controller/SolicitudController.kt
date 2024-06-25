package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.domain.Solicitud
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.service.SolicitudService
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class SolicitudController {
    @Autowired
    private lateinit var solicitudService: SolicitudService

    @PostMapping("/solicitud/{idCreador}/amistad/{idNuevoAmigo}")
    fun crearSolicitudDeAmistad(@PathVariable idCreador: Long, @PathVariable idNuevoAmigo: Long, @RequestBody solicitudAmistad: SolicitudAmistadDTO): ResponseEntity<String> {
        solicitudService.crearSolicitudDeAmistad(idCreador, idNuevoAmigo, solicitudAmistad.mensaje)
        return ResponseEntity.ok("Solicitud enviada con exito");
    }

    // Endpoint que nos devuele la solicitud en caso que un cierto usuario (creador)
    // tenga una solicitud pendiente de ser aceptada por otro (idNuevoAmigo).
    // Si no tiene ninguna devuelve un 404
    @GetMapping("/solicitud-pendiente/{idCreador}/amistad/{idNuevoAmigo}")
    fun tieneSolicitudDeAmistadPendiente(@PathVariable idCreador: Long, @PathVariable idNuevoAmigo: Long): SolicitudAmistadResponseDTO {
        val solicitud = solicitudService.tieneSolicitudDeAmistadPendiente(idCreador, idNuevoAmigo);
        return SolicitudAmistadResponseDTO.from((solicitud))
    }

    // Trae todas las peticiones de amistad pendientes de un usuario
    @GetMapping("/solicitudes-pendientes/{idUsuario}")
    fun getSolicitudesPendientes(@PathVariable idUsuario: Long): List<SolicitudAmistadPendienteDTO> {
        val solicitudes = solicitudService.getSolicitudesPendientes(idUsuario);
        return solicitudes.map{SolicitudAmistadPendienteDTO.from((it))}
    }

    // Acepta una solicitud
    @PostMapping("/solicitud/{idSolicitud}/aceptar")
    fun aceptarSolicitud(@PathVariable idSolicitud: Long): SolicitudAmistadResponseDTO {
        val solicitud = solicitudService.aceptarSolicitud(idSolicitud)
        return SolicitudAmistadResponseDTO.from(solicitud);
    }

    // Rechaza una solicitud
    @PostMapping("/solicitud/{idSolicitud}/rechazar")
    fun rechazarSolicitud(@PathVariable idSolicitud: Long): SolicitudAmistadResponseDTO {
        val solicitud = solicitudService.rechazarSolicitud(idSolicitud)
        return SolicitudAmistadResponseDTO.from(solicitud);
    }
}
