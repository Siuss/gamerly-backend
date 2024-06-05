package com.gamerly.projectgamerly.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn


@Entity
class Resenia(
    @JoinColumn(name = "usuario_id")
    var idUsuarioEmisor: Int,
    @JoinColumn(name = "usuario_id")
    var idUsuarioReceptor: Int,
    @Column
    var puntaje: Int,
    @Column
    var comentario: String) {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
var id: Long = 0

    fun sendResenia(usuarioReceptor: Usuario, puntaje: Int, comentario: String) {
        val resenia = Resenia(
            idUsuarioEmisor = this.id.toInt(),
            idUsuarioReceptor = usuarioReceptor.id.toInt(),
            puntaje = puntaje,
            comentario = comentario
        )
        this.reseniasEnviadas.add(resenia)
    }
}