package com.gamerly.projectgamerly.domain
import jakarta.persistence.*

@Entity
@Table(name = "chat")
class Chat (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne()
    var usuario1: Usuario,
    @ManyToOne()
    var usuario2: Usuario,
    @OneToMany
    @OrderColumn(name = "id")
    var mensajes: MutableList<Mensaje>
)
