package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Usuario
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<Usuario, Long>{
    @EntityGraph(attributePaths = ["juegosPreferidos", "diasPreferidos", "plataformas"])
    override fun findById(id: Long): Optional<Usuario>
}