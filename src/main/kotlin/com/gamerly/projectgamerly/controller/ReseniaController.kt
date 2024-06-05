package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.service.ReseniaService
import com.gamerly.projectgamerly.service.UsuarioService
import com.gamerly.projectgamerly.service.toDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class ReseniaController {
    @Autowired
    lateinit var reseniaService: ReseniaService

    @PostMapping("/crear-Reseña")
    fun crearResenia(@RequestBody reseniaBody: ReseniaCreacionDTO): ReseniaCreacionDTO =
        reseniaService.crearResenia(reseniaBody).toDTO()
}


