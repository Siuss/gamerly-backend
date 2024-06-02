package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario
import java.time.LocalDate

class UsuarioCreacionDTO {
    lateinit var nombre: String
    lateinit var fechaNacimiento: LocalDate
    lateinit var email: String
    lateinit var password: String

    companion object {
        fun fromUsuario(usuario: Usuario): UsuarioCreacionDTO = UsuarioCreacionDTO().also {
            it.nombre = usuario.nombre
            it.fechaNacimiento = usuario.fechaDeNacimiento
            it.email = usuario.email
            it.password = usuario.password
        }
    }
}