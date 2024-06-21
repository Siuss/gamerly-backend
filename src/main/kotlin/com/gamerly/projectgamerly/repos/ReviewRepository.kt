package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Resenia
import org.springframework.data.repository.CrudRepository
import java.util.Optional

//aca cremos un metodo para verificar si tiene comentarios
interface ReviewRepository : CrudRepository<Resenia, Long>{

    fun findByUsuarioIdAndUsuarioReceptorId(usuarioId: Long, usuarioReceptorId: Long): Optional<Resenia>

}