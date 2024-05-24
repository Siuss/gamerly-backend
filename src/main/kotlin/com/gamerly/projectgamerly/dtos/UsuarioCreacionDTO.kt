package com.gamerly.projectgamerly.dtos

import java.time.LocalDate

class UsuarioCreacionDTO() {
    lateinit var nombre: String
    lateinit var fechaNacimiento: LocalDate
    lateinit var email: String
    lateinit var password: String
}