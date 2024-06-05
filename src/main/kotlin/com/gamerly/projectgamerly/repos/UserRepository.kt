package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Usuario
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserRepository : CrudRepository<Usuario, Long>{
    @Query("""
        SELECT u, AVG(r.puntaje)
        FROM Usuario u
        JOIN u.resenias r
        JOIN u.juegosPreferidos j
        GROUP BY u, j
        HAVING AVG(r.puntaje) > :puntaje
        AND j IN :juegos
    """)
    fun findUsuariosSegunFiltros(@Param("juegos") juegosEnComun: List<String>, @Param("puntaje") puntaje: Long): List<Usuario>
}