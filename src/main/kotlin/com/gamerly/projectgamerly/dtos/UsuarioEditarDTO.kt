package com.gamerly.projectgamerly.dtos

class UsuarioEditarDTO() {
    var nombre: String? = null
    var foto: String? = null
    var fechaNacimiento: String? = null
    var nacionalidad: String? = null
    var plataformas: Set<String>? = null
    var juegos: Set<String>? = null
    var horarios: Set<String>? = null
    //TODO: una vez implementados los horarios, ver como editarlos
    //var userDiscord: String? = null
}
