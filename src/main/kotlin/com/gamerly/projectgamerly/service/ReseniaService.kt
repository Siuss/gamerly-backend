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
    @Autowired
    private lateinit var usuarioService: UsuarioService
    @Autowired lateinit var reseniaRepository : ReviewRepository
    @Autowired lateinit var usuarioRepository : UserRepository

    @Transactional()
    fun crearResenia(reseniaBody: ReseniaCreacionDTO, idUsuarioEmisor : Long, idUsuarioReceptor : Long): Resenia {
        if (idUsuarioReceptor == idUsuarioEmisor) {
            throw ReseniaException("No se puede dejar una reseña a si mismo")
        }
        val usuarioReceptor = usuarioRepository.findById(idUsuarioReceptor).get()
        val existingResenia = usuarioReceptor.resenias.find { it.idUsuarioEmisor == idUsuarioEmisor }
        if (existingResenia != null) {
            throw ReseniaException("Ya has dejado una reseña a este usuario")
        }
        val nuevaResenia = Resenia(
            idUsuarioEmisor,
            reseniaBody.puntaje,
            reseniaBody.comentario,
            LocalDate.now(),
            LocalTime.now()
        )
        usuarioReceptor.addResenia(nuevaResenia)
        usuarioRepository.save(usuarioReceptor)
        return reseniaRepository.save(nuevaResenia)
    }

    fun getResenias(idUsuario: Long): MutableSet<Resenia> {
        val usuario = usuarioService.getUsuario(idUsuario)
        return usuario.resenias
    }
}
