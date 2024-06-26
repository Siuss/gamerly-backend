package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario

class UsuarioBusquedaDto() {
    var id : Long = 0
    var nombre: String = ""
    var foto: String = ""
    var email: String = ""
    var puntaje: Long = 0

    constructor(
        usuario: Usuario
    ) : this() {
        this.id = usuario.id
        this.nombre = usuario.nombre
        this.foto = usuario.foto
        this.email = usuario.email
        this.puntaje = usuario.resenias.map { it.puntaje }.average().toLong()
    }
}