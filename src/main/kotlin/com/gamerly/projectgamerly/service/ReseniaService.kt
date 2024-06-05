package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ReseniaService(private val reseniaRepository: ReseniaRepository) {

    fun crearResenia(reseniaBody: ReseniaCreacionDTO): Resenia {
        val nuevaResenia = Resenia(
            comentario = reseniaBody.comentario,
            puntaje = reseniaBody.puntaje,
            idUsuario = reseniaBody.idUsuario,
            foto = reseniaBody.foto
        )
        return reseniaRepository.save(nuevaResenia)
    }
}

// Extensión para convertir una entidad a DTO
fun Resenia.toDTO(): ReseniaCreacionDTO {
    return ReseniaCreacionDTO(
        comentario = this.comentario,
        puntaje = this.puntaje,
        idUsuario = this.idUsuario,
        foto = this.foto
    )
}

