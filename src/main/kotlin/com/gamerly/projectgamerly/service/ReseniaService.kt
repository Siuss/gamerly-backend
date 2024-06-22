package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.ReviewRepository
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.utilities.ReseniaException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.LocalTime

@Service
class ReseniaService() {
    @Autowired lateinit var reseniaRepository : ReviewRepository
    @Autowired lateinit var usuarioRepository : UserRepository

    @Transactional()
    fun crearResenia(reseniaBody: ReseniaCreacionDTO, idUsuario : Long, idUsuarioReceptor : Long): Resenia {
        val usuario = usuarioRepository.findById(idUsuario)
        val existingResenia = reseniaRepository.findAll().find { it.idUsuarioEmisor == idUsuario }
        if (existingResenia != null) {
            throw ReseniaException("Ya has dejado una reseña a este usuario")
        }
        val nuevaResenia = Resenia(
            usuario.get().id,
            //idUsuarioReceptor,
            reseniaBody.puntaje,
            reseniaBody.comentario,
            LocalDate.now(),
            LocalTime.now()
        )

        return reseniaRepository.save(nuevaResenia)
    }
}
