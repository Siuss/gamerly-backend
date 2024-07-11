package com.gamerly.projectgamerly.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Reporte(
    @ManyToOne
    var usuarioCreador: Usuario,

    @ManyToOne
    var usuarioReportado: Usuario,

    @Column
    var comentario: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column
    var fecha: LocalDateTime = LocalDateTime.now()

}