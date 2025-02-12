package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Usuario

class UsuarioBusquedaDto() {
    var id : Long = 0
    var nombre: String = ""
    var fileName: String = ""
    var email: String = ""
    var puntaje: Long = 0
    var plataforma: String = ""

    fun getPlataforma(usuario: Usuario): String{
        if(usuario.plataformas.size > 0){
            return usuario.plataformas.toList().get(0).nombre
        }else{
            return ""
        }
    }

    constructor(
        usuario: Usuario
    ) : this() {
        this.id = usuario.id
        this.nombre = usuario.nombre
        this.fileName = usuario.fileName
        this.email = usuario.email
        this.puntaje = usuario.calculoPuntaje()
        this.plataforma = getPlataforma(usuario)
    }
}