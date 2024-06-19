package com.gamerly.projectgamerly.domain

import jakarta.persistence.*

@Entity
data class Juego(
    @Column
    val nombre: String,
    @Column
    val imagen: String,
    @ElementCollection
    @CollectionTable(name = "plataformas")
    @Column
    val plataformas: List<String> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
