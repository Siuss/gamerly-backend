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
//    lateinit var diasHorariosPreferidos: Set<String>
    lateinit var plataformas: Set<String>
    lateinit var nacionalidad: String
    var reputacion: Long = 0
    var resenias: Set<Resenia> = hashSetOf()

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
//        this.diasHorariosPreferidos = usuario.diasHorariosPreferidos
        this.plataformas = usuario.plataformas
        this.nacionalidad = usuario.nacionalidad
    }
}