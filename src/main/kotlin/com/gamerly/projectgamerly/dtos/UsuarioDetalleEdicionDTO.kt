package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.domain.DiaHorarioPreferido

class UsuarioDetalleEdicionDTO() {
    var id : Long = 0
    lateinit var nombre: String
    lateinit var discord: String
    lateinit var foto: String
    lateinit var email: String
    lateinit var password: String
    lateinit var juegosPreferidos: List<String>
    lateinit var diasHorariosPreferidos: Set<DiaHorarioPreferido>
    lateinit var plataformas: List<String>
    lateinit var nacionalidad: String
    lateinit var fechaDeNacimiento: String

    constructor(
        usuario: Usuario
    ) : this() {
        this.id = usuario.id
        this.nombre = usuario.nombre
        this.discord = usuario.discord
        this.foto = usuario.foto
        this.email = usuario.email
        this.password = usuario.password
        this.juegosPreferidos = usuario.juegosPreferidos.map{it.nombre}
        this.diasHorariosPreferidos = usuario.diasHorariosPreferidos
        this.plataformas = usuario.plataformas.map{it.nombre}
        this.nacionalidad = usuario.nacionalidad
        this.fechaDeNacimiento = formatFecha(usuario.fechaDeNacimiento)
    }
}