package com.gamerly.projectgamerly.domain

class Notificacion(
    val tokenReceptor: String,
    val titulo: String,
    val mensaje: String? = null) {}