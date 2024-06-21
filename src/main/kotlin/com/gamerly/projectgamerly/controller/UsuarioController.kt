package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.dtos.InputBusquedaDTO
import com.gamerly.projectgamerly.dtos.ReseniasDTO
import com.gamerly.projectgamerly.dtos.UsuarioBusquedaDto
import com.gamerly.projectgamerly.dtos.CredencialesDTO
import com.gamerly.projectgamerly.dtos.UsuarioCreacionDTO
import com.gamerly.projectgamerly.dtos.UsuarioDetalleDTO
import com.gamerly.projectgamerly.dtos.UsuarioLoginDTO
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class UsuarioController {
    @Autowired
    lateinit var usuarioService: UsuarioService

    @PostMapping("/sign-up")
    fun crearUsuario(@RequestBody usuarioNuevo: UsuarioCreacionDTO) {
        usuarioService.crearUsuario(usuarioNuevo)
    }
    
    @GetMapping("/buscar")
    fun busquedaAvanzada(@RequestBody inputBusqueda: InputBusquedaDTO): List<UsuarioBusquedaDto> {
        return usuarioService.busquedaAvanzada(inputBusqueda)
    }
    
    @PostMapping("/login")
    fun loginUsuario(@RequestBody credenciales: CredencialesDTO): UsuarioLoginDTO {
        return usuarioService.login(credenciales)
    }

    @GetMapping("/detalle/{idUsuario}")
    fun detalleUsuario(@PathVariable idUsuario: Long): UsuarioDetalleDTO {
        return usuarioService.getUsuarioDetalle(idUsuario)
    }

    @GetMapping("/comentarios/{idUsuario}")
    fun comentariosUsuario(@PathVariable idUsuario: Long) : List<ReseniasDTO>{
        return usuarioService.comentariosUsuario(idUsuario)
    }

    @GetMapping("/")
    fun getAllUsers(): List<UsuarioDetalleDTO> {
        return usuarioService.getAllUsers()
    }

    @DeleteMapping("/usuarios/{idUsuario}")
    fun deleteUsuario(@PathVariable idUsuario: Long): UsuarioDetalleDTO {
        return usuarioService.deleteUsuario(idUsuario)
    }

}
