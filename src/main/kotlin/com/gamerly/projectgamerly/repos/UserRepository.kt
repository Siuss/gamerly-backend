package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Usuario
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserRepository : CrudRepository<Usuario, Long>{
    @Query("""
        SELECT usuario, AVG(resenia.puntaje)
        FROM Usuario usuario
        JOIN usuario.resenias resenia
        GROUP BY usuario
        HAVING AVG(resenia.puntaje) > :puntaje
    """)
    fun findUsuariosSegunFiltros(@Param("puntaje") puntaje: Long): List<Usuario>
}