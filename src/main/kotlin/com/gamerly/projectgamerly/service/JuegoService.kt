package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Juego
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File

@Service
class JuegoService {
    var info = ClassPathResource("game_info.csv").file
    var infoFiltrada = csvReader().readAllWithHeader(info).filter {
        it["rating"]!!.toDouble() > 0
    }

    fun getJuegos(nombre: String): List<Juego> {
        val listaJuegos = infoFiltrada.filter {
            it["name"]!!.lowercase().contains(nombre.lowercase())
        }
        return listaJuegos.map {
            Juego(
                it["name"]!!,
                "",
                it["platforms"]!!.split("||")
            )
        }
    }
}