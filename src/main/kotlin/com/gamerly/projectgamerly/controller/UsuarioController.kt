package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.dtos.UsuarioBusquedaDto
import com.gamerly.projectgamerly.repos.UsuarioRepository
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController;

@RestController
class UsuarioController {
    @Autowired
    lateinit var usuarioService: UsuarioService

    @GetMapping("/buscar")
    fun busquedaAvanzada(@RequestParam(required = false) juegosEnComun: List<String>?,@RequestParam(required = false) puntaje: Long?): List<UsuarioBusquedaDto> {
        return usuarioService.busquedaAvanzada(juegosEnComun, puntaje)
    }
}
