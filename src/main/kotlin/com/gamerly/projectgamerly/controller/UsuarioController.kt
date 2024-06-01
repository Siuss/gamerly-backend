package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.dtos.UsuarioBusquedaDto
import com.gamerly.projectgamerly.dtos.UsuarioCreacionDTO
import com.gamerly.projectgamerly.dtos.UsuarioDetalleDTO
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UsuarioController {
    @Autowired
    lateinit var usuarioService: UsuarioService

    @PostMapping("/sign-up")
    fun crearUsuario(@RequestBody usuarioNuevo: UsuarioCreacionDTO) {
        usuarioService.crearUsuario(usuarioNuevo)
    }

//    @GetMapping("/buscar")
//    fun busquedaAvanzada(@RequestParam(required = false) juegosEnComun: List<String>?,@RequestParam(required = false) puntaje: Long?): List<UsuarioBusquedaDto> {
//        return usuarioService.busquedaAvanzada(juegosEnComun, puntaje)
//    }

    @GetMapping("/detalle/{idUsuario}")
    fun detalleUsuario(@PathVariable idUsuario: Long): UsuarioDetalleDTO {
        return usuarioService.getUsuario(idUsuario)
    }

    @DeleteMapping("/usuarios/{idUsuario}")
    fun deleteUsuario(@PathVariable idUsuario: Long): UsuarioDetalleDTO {
        return usuarioService.deleteUsuario(idUsuario)
    }

    @ExceptionHandler(Exception::class)
    fun excepcionGenerica(exception: Exception): ResponseEntity<String> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }
}
