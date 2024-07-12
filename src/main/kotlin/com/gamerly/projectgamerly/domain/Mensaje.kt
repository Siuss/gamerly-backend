package com.gamerly.projectgamerly.domain
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "mensaje")
class Mensaje (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne()
    var usuarioCreador: Usuario,
    @ManyToOne()
    var usuarioReceptor: Usuario,
    @Column
    var contenido: String,
    @Column
    var fecha: LocalDateTime,
    @Column
    var leido: Boolean = false
)
