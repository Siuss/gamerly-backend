package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Chat
import com.gamerly.projectgamerly.domain.Juego
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ChatRepository: JpaRepository<Chat, Long> {
    @EntityGraph(attributePaths = ["mensajes"])
    fun findByUsuario1_idAndUsuario2_id(idUsuario1: Long, idUsuario2: Long): Optional<Chat>

    @EntityGraph(attributePaths = ["mensajes"])
    fun findAllByUsuario1_idOrUsuario2_id(idUsuario1: Long, idUsuario2: Long): List<Chat>

    @EntityGraph(attributePaths = ["mensajes"])
    override fun findById(idChat: Long): Optional<Chat>
}