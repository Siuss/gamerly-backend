package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario
import java.time.LocalDate

class UsuarioCreacionDTO {
    lateinit var nombre: String
    lateinit var fechaNacimiento: String
    lateinit var email: String
    lateinit var password: String
}