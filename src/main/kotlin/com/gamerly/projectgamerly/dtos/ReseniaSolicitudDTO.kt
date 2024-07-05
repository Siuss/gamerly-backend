package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Resenia

class ReseniaSolicitudDTO {
    var id: Long = 0
    var idUsuarioEmisor: Long = 0

    companion object {
        fun from(resenia: Resenia) : ReseniaSolicitudDTO = ReseniaSolicitudDTO().also {
            it.id = resenia.id
            it.idUsuarioEmisor = resenia.idUsuarioEmisor
        }
    }

}