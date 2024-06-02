package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import java.time.LocalDate

class UsuarioDetalleDTO() {
    var id : Long = 0
    lateinit var nombre: String
    lateinit var foto: String
    lateinit var fechaDeNacimiento: LocalDate
    lateinit var email: String
    lateinit var password: String
    lateinit var juegosPreferidos: List<String>
    lateinit var diasPreferidos: List<String>
    var reputacion: Long = 0
    lateinit var resenias: List<Resenia>

    constructor(
        usuario: Usuario
    ) : this() {
        this.id = usuario.id
        this.nombre = usuario.nombre
        this.fechaDeNacimiento = usuario.fechaDeNacimiento
        this.foto = usuario.foto
        this.email = usuario.email
        this.password = usuario.password
        this.juegosPreferidos = usuario.juegosPreferidos
        this.diasPreferidos = usuario.diasPreferidos

    }
}