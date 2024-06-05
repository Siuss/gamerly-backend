package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.dtos.InputBusquedaDTO
import com.gamerly.projectgamerly.dtos.UsuarioBusquedaDto
import com.gamerly.projectgamerly.dtos.UsuarioCreacionDTO
import com.gamerly.projectgamerly.dtos.UsuarioDetalleDTO
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    @GetMapping("/detalle/{idUsuario}")
    fun detalleUsuario(@PathVariable idUsuario: Long): UsuarioDetalleDTO {
        return usuarioService.getUsuario(idUsuario)
    }
}
