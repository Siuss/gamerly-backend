package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.service.ReseniaService
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@CrossOrigin(origins = ["*"])
class ReseniaController {
    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var reseniaService: ReseniaService

    @PostMapping("/crear-resenia/{idUsuarioEmisor}/{idUsuarioReceptor}")
    fun crearResenia(@PathVariable idUsuarioEmisor : Long, @PathVariable idUsuarioReceptor : Long, @RequestBody reseniaBody: ReseniaCreacionDTO): ReseniaCreacionDTO {
        return ReseniaCreacionDTO.fromResenia(reseniaService.crearResenia(reseniaBody, idUsuarioEmisor, idUsuarioReceptor))
    }

    @GetMapping("/resenias-pendientes/{idUsuario}")
    fun getReseniasPendientes(@PathVariable idUsuario : Long): List<ReseniaPendienteDTO> {
        val resenias = reseniaService.getReseniasPendientes(idUsuario)
        return resenias.map{ReseniaPendienteDTO.fromResenia(usuarioService.getUsuario(it.idUsuarioEmisor), it)}
    }

    @GetMapping("/resenias/{idUsuario}")
    fun getResenias(@PathVariable idUsuario : Long): List<ReseniasDTO> {
        val resenias = reseniaService.getResenias(idUsuario)
        return resenias.map{ReseniasDTO.fromResenias(usuarioService.getUsuario(it.idUsuarioEmisor), it)}
    }

    @GetMapping("/{idUsuarioReceptor}/tiene-resenia-de/{idUsuarioCreador}")
    fun getResenia(@PathVariable idUsuarioReceptor : Long, @PathVariable idUsuarioCreador : Long): Boolean {
        if(reseniaService.tieneReseniaDe(idUsuarioReceptor, idUsuarioCreador)){
            return true
        }else {
            return false
        }
    }


    @PostMapping("/resenias/{idResenia}/aceptar/{idUsuarioLogueado}")
    fun aceptarSolicitud(@PathVariable idResenia: Long, @PathVariable idUsuarioLogueado: Long): ReseniaSolicitudDTO {
        val resenia = reseniaService.aceptarReseniaPendiente(idResenia, idUsuarioLogueado)
        return ReseniaSolicitudDTO.from(resenia);
    }

    @PostMapping("/resenias/{idResenia}/rechazar/{idUsuarioLogueado}")
    fun rechazarSolicitud(@PathVariable idResenia: Long, @PathVariable idUsuarioLogueado: Long): ReseniaSolicitudDTO {
        val resenia = reseniaService.rechazarReseniaPendiente(idResenia, idUsuarioLogueado)
        return ReseniaSolicitudDTO.from(resenia);
    }
}


