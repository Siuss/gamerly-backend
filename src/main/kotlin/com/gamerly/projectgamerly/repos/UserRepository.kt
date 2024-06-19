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
    @Query("""
        SELECT u
        FROM Usuario u
        JOIN u.resenias r
        JOIN u.juegosPreferidos j
        JOIN u.diaFavorito df
        JOIN u.horariosPreferidos dh
        GROUP BY u, j, df, dh
        HAVING (AVG(r.puntaje) > :puntaje OR :puntaje IS NULL)
        AND (j IN :juegos OR :juegos IS NULL)
        AND (dh IN :diasHorarios OR :diasHorarios IS NULL)
    """)
    fun findUsuariosSegunFiltros(
        @Param("juegos") juegosEnComun: Set<String>?,
        @Param("puntaje") puntaje: Long?,
        @Param("diasHorarios") diasHorarios: List<String>?
    ): List<Usuario>
    @EntityGraph(attributePaths = ["juegosPreferidos", "horariosPreferidos", "plataformas","diaFavorito" ,"resenias"])
    override fun findById(id: Long): Optional<Usuario>

    @Query("SELECT r FROM Resenia r WHERE r.idUsuarioReceptor = :userId")
    fun findReseniasByUsuarioId(@Param("userId") userId: Long): List<Resenia>
  
    @EntityGraph(attributePaths = ["juegosPreferidos", " horariosPreferidos","diaFavorito", "plataformas"])
    fun findByEmailAndPassword(email: String, password: String): Optional<Usuario>

    @EntityGraph(attributePaths = ["juegosPreferidos", "plataformas"])
    fun findByEmail(email: String): Optional<Usuario>
}