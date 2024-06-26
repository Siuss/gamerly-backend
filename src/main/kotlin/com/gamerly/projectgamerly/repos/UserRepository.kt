package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.HorariosFavoritos
import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
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
        JOIN u.diasHorariosPreferidos dh
        GROUP BY u, j, dh
        HAVING (AVG(r.puntaje) > :puntaje OR :puntaje IS NULL)
        AND (dh.diaDeLaSemana IN :dias OR :dias IS NULL)
        AND (dh.horarioFavorito IN :horarios OR :horarios IS NULL)
    """)
    fun findUsuariosSegunFiltros(
        @Param("puntaje") puntaje: Long?,
        @Param("dias") dias: List<DiaDeLaSemana>?,
        @Param("horarios") horarios: List<HorariosFavoritos>?
    ): List<Usuario>
    @EntityGraph(attributePaths = ["juegosPreferidos", "diasHorariosPreferidos", "plataformas", "resenias", "amigos"])
    override fun findById(id: Long): Optional<Usuario>

    @EntityGraph(attributePaths = ["juegosPreferidos", "diasHorariosPreferidos", "plataformas", "resenias" ,"amigos"])
    override fun findAll(): MutableIterable<Usuario>
  
    @EntityGraph(attributePaths = ["diasHorariosPreferidos", "plataformas", "juegosPreferidos"])
    fun findByEmailAndPassword(email: String, password: String): Optional<Usuario>

    @EntityGraph(attributePaths = ["plataformas", "juegosPreferidos"])
    fun findByEmail(email: String): Optional<Usuario>

    @EntityGraph(attributePaths = ["diasHorariosPreferidos", "plataformas", "juegosPreferidos","resenias"])
    fun findAllByjuegosPreferidos_Id(juegoId: Long): List<Usuario>

}