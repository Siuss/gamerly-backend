package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Juego
import com.gamerly.projectgamerly.dtos.ComunidadDTO
import com.gamerly.projectgamerly.repos.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class JuegoService {
    @Autowired
    lateinit var juegoRepository: GameRepository

    fun getJuegosPorNombre(nombre: String): List<Juego> {
        return juegoRepository.findJuegosByNombreContainingIgnoreCase(nombre)
    }

    fun getJuegosConLimite(numero: Int?): List<Juego> {
        return if (numero != null) {
            val pageable = PageRequest.of(0, numero)
            juegoRepository.findAll(pageable).toList()
        } else {
            juegoRepository.findAll()
        }
    }

    fun getAllComunidad() : List<ComunidadDTO> {
        val communityList = juegoRepository.findAll().map {
            ComunidadDTO.fromComunidad(it)
        }
        return communityList
    }

}