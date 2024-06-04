package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario

class UsuarioLoginDTO {
    var id : Long = 0
    lateinit var nombre: String
    lateinit var foto: String
    lateinit var email: String
    lateinit var juegosPreferidos: List<String>
    lateinit var plataformas: Set<String>
    lateinit var diasPreferidos: Set<String>
    lateinit var nacionalidad: String
    var reputacion: Long = 0

    companion object {
        fun from(usuario: Usuario): UsuarioLoginDTO = UsuarioLoginDTO().also {
            it.id = usuario.id
            it.nombre = usuario.nombre
            it.foto = usuario.foto
            it.email = usuario.email
            it.juegosPreferidos = usuario.juegosPreferidos
            it.diasPreferidos = usuario.diasPreferidos
            it.plataformas = usuario.plataformas
            it.nacionalidad = usuario.nacionalidad
        }
    }
}