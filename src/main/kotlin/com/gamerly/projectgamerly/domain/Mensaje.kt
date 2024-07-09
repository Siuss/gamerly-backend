package com.gamerly.projectgamerly.domain
import jakarta.persistence.*

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
    var contenido: String
)
