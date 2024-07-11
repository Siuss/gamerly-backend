package com.gamerly.projectgamerly.controller

import com.gamerly.projectgamerly.domain.Chat
import com.gamerly.projectgamerly.domain.Juego
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.service.ChatService
import com.gamerly.projectgamerly.service.JuegoService
import com.gamerly.projectgamerly.service.ReporteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
class ReporteController {
    @Autowired
    lateinit var reporteService: ReporteService

    @PostMapping("{idCreador}/nuevo-reporte/{idReportado}")
    fun nuevoReporte(
        @PathVariable idCreador: Long,
        @PathVariable idReportado: Long,
        @RequestBody reporte: NuevoReporteDTO
    ): Long {
        return reporteService.nuevoReporte(idCreador, idReportado, reporte)
    }

    // No devolvemos el objeto reporte, solo la cantidad para evitar que sea posible leerlos
    // y asi mantener el anonimato
    @GetMapping("/reportes/{idUsuario}")
    fun getCantidadReportesDelUsuario(@PathVariable idUsuario: Long): Long {
        return reporteService.getCantidadReportesDelUsuario(idUsuario)
    }
}