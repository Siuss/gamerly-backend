package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Resenia

data class ReseniaCreacionDTO(
    var comentario: String,
    var puntaje: Int
) {
    companion object {
        fun fromResenia(resenia: Resenia): ReseniaCreacionDTO = ReseniaCreacionDTO(
            comentario = resenia.comentario,
            puntaje = resenia.puntaje
        )
    }
}

