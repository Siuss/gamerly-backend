package com.gamerly.projectgamerly.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
class Resenia(
    @Column()
    var idUsuarioEmisor: Int,
    @Column
    var idUsuarioReceptor: Int,
    @Column
    var puntaje: Int,
    @Column
    var comentario: String) {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
var id: Long = 0
}