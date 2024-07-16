package com.gamerly.projectgamerly.controller

import com.gamerly.projectgamerly.domain.Juego
import com.gamerly.projectgamerly.domain.Plataformas
import com.gamerly.projectgamerly.dtos.ComunidadDTO
import com.gamerly.projectgamerly.service.JuegoService
import com.gamerly.projectgamerly.service.PlataformaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
class PlataformaController {
    @Autowired
    lateinit var plataformaService: PlataformaService

    @GetMapping("/listaPlataformas/{nombre}")
    fun traerPlataformasPorNombre(@PathVariable nombre: String): List<String> {
        return plataformaService.getPlataformasPorNombre(nombre).map{it.nombre}
    }
}