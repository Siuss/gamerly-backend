package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario

class UsuarioBusquedaJuegosDTO() {
    var id : Long = 0
    var nombre: String = ""
    var foto: String = ""
    var email: String = ""
    var puntaje: Long = 0
    lateinit var juegosPreferidos: List<String>
    lateinit var plataformas: Set<String>

    constructor(
        usuario: Usuario
    ) : this() {
        this.id = usuario.id
        this.nombre = usuario.nombre
        this.foto = usuario.foto
        this.email = usuario.email
        this.puntaje = usuario.resenias.map { it.puntaje }.average().toLong()
        this.juegosPreferidos = usuario.juegosPreferidos.map { it.nombre }
        this.plataformas = usuario.plataformas.map { it.nombre }.toSet()
    }
}