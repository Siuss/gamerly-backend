package com.gamerly.projectgamerly.dtos;

import com.gamerly.projectgamerly.domain.Plataformas
import com.gamerly.projectgamerly.domain.Usuario

class AmigoDTO {
    var id : Long = 0
    lateinit var nombre: String
    lateinit var foto: String
    lateinit var juegosPreferidos: List<String>
    lateinit var plataformas: Set<String>

    companion object {
        fun from(usuario: Usuario): AmigoDTO = AmigoDTO().also { dto ->
            dto.id = usuario.id
            dto.nombre = usuario.nombre
            dto.foto = usuario.foto
            dto.juegosPreferidos = usuario.juegosPreferidos.map { it.nombre }
            dto.plataformas = usuario.plataformas.map { it.nombre }.toSet()
        }
    }
}