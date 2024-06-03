package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserRepository : CrudRepository<Usuario, Long>{

    @Query("SELECT r FROM Resenia r WHERE r.idUsuarioReceptor = :userId")
    fun findReseniasByUsuarioId(@Param("userId") userId: Long): List<Resenia>
}