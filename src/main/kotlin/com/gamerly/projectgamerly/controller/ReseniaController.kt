package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.service.ReseniaService
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @GetMapping("/resenias/{idUsuario}")
    fun getResenia(@PathVariable idUsuario : Long): List<ReseniasDTO> {
        val resenias = reseniaService.getResenias(idUsuario)
        return resenias.map{ReseniasDTO.fromResenias(usuarioService.getUsuario(it.idUsuarioEmisor), it)}
    }
}


