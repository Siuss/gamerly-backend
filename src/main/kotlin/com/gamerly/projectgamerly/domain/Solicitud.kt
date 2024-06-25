package com.gamerly.projectgamerly.domain
import jakarta.persistence.*

@Entity
@Table(name = "solicitudes")
class Solicitud(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToOne
    var usuarioCreador: Usuario,
    @OneToOne
    var usuarioReceptor: Usuario,
    @Column
    var mensaje: String
)



