package com.gamerly.projectgamerly.controller

import com.gamerly.projectgamerly.domain.Juego
import com.gamerly.projectgamerly.dtos.ComunidadDTO
import com.gamerly.projectgamerly.service.JuegoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class JuegoController {
    @Autowired
    lateinit var juegoService: JuegoService

    @GetMapping("/listaJuegos/{nombre}")
    fun traerJuegos(@PathVariable(required = false) nombre: String): List<Juego> {
        return juegoService.getJuegos(nombre)
    }

    @GetMapping("/comunidad")
    fun traerComunidad(): List<ComunidadDTO> {
        return juegoService.getAllComunidad()
    }
}