package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : CrudRepository<Usuario, Long>{
    @EntityGraph(attributePaths = ["juegosPreferidos", "diasPreferidos", "plataformas"])
    override fun findById(id: Long): Optional<Usuario>

    @Query("SELECT r FROM Resenia r WHERE r.idUsuarioReceptor = :userId")
    fun findReseniasByUsuarioId(@Param("userId") userId: Long): List<Resenia>
  
    @EntityGraph(attributePaths = ["juegosPreferidos", "diasPreferidos", "plataformas"])
    fun findByEmailAndPassword(email: String, password: String): Optional<Usuario>
}