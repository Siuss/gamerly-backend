package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario

class UsuarioLoginDTO {
    var id : Long = 0
    lateinit var nombre: String
    lateinit var foto: String
    lateinit var email: String
    lateinit var juegosPreferidos: List<String>
    lateinit var plataformas: Set<String>
//    lateinit var diasPreferidos: Set<String>
    lateinit var nacionalidad: String
    var reputacion: Long = 0

    companion object {
        fun from(usuario: Usuario): UsuarioLoginDTO = UsuarioLoginDTO().also { dto ->
            dto.id = usuario.id
            dto.nombre = usuario.nombre
            dto.foto = usuario.foto
            dto.email = usuario.email
            dto.juegosPreferidos = usuario.juegosPreferidos.map { it.nombre }
//            dto.diasPreferidos = usuario.diasHorariosPreferidos
            dto.plataformas = usuario.plataformas
            dto.nacionalidad = usuario.nacionalidad
        }
    }
}