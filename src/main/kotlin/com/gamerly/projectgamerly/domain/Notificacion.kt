package com.gamerly.projectgamerly.domain

class Notificacion(
    var tokenReceptor: String,
    var titulo: String,
    var mensaje: String? = null,
    var data: MutableMap<String, Any>? = null
) {}