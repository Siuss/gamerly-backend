package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Juego

class InputBusquedaDTO {
    var juegos: List<String>? = null
    var puntaje: Long? = null
    var dias: List<String>? = null
    var horarios: List<String>? = null
}