package com.gamerly.projectgamerly.domain

import jakarta.persistence.*

@Entity
data class Juego(
    @Column
    val nombre: String,
    @Column
    val imagen: String,
    @ElementCollection(targetClass = Plataformas::class)
    @CollectionTable(name = "plataformas")
    @Column
    val plataformas: List<Plataformas> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
