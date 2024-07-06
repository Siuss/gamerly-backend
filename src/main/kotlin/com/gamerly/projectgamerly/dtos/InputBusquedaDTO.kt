package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.HorariosFavoritos
import com.gamerly.projectgamerly.domain.Juego
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana

class InputBusquedaDTO {
    var dias: List<DiaDeLaSemana>? = null
    var momentos: List<HorariosFavoritos>? = null
    var resenia: Long? = null
    var nombre: String? = null
}