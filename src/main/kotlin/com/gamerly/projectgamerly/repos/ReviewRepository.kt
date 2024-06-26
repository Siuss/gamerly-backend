package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Resenia
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.Optional


interface ReviewRepository : CrudRepository<Resenia, Long>{
    fun findReseniaByIdUsuarioEmisor(idUsuarioEmisor: Long): Resenia?
}