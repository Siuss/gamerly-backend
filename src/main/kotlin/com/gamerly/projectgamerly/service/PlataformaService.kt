package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Juego
import com.gamerly.projectgamerly.domain.Plataformas
import com.gamerly.projectgamerly.dtos.ComunidadDTO
import com.gamerly.projectgamerly.repos.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PlataformaService {
    fun getPlataformasPorNombre(nombre: String): List<Plataformas> {
        if(nombre.isEmpty()){
            return Plataformas.entries
        }

        return Plataformas.entries.filter { it.nombre.contains(nombre) }
    }
}