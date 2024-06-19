package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Juego
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository: JpaRepository<Juego, Long> {
    @EntityGraph(attributePaths = ["plataformas"])
    fun findJuegosByNombreContainingIgnoreCase(nombre: String, pageable: Pageable): Page<Juego>

    @EntityGraph(attributePaths = ["plataformas"])
    override fun findAll(pageable: Pageable): Page<Juego>

    @EntityGraph(attributePaths = ["plataformas"])
    override fun findAll(): List<Juego>
}