package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario

class UsuarioLoginDTO {
    lateinit var userName : String
    lateinit var password : String

    companion object {
        fun from(user: Usuario) : UsuarioLoginDTO = UsuarioLoginDTO().also {
            it.userName = user.email
            it.password = user.password
        }
    }
}