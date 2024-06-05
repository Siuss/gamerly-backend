package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Juego

class InputBusquedaDTO {
    lateinit var juegos: List<String>
    var puntaje: Long? = null
    lateinit var dias: List<String>
    lateinit var horarios: List<String>
}