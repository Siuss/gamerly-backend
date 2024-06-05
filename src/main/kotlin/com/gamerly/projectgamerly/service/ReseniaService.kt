package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.ReviewRepository
import com.gamerly.projectgamerly.repos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ReseniaService() {
    @Autowired lateinit var reseniaRepository : ReviewRepository
    @Autowired lateinit var usuarioRepository : UserRepository
    fun crearResenia(reseniaBody: ReseniaCreacionDTO, idUsuario : Long, idUsuarioReceptor : Long): Resenia {
        val usuario = usuarioRepository.findById(idUsuario)
        val nuevaResenia = Resenia(
            usuario.get().id,
            idUsuarioReceptor,
            reseniaBody.puntaje,
            reseniaBody.comentario,

        )
        usuario.get().resenias.add(nuevaResenia)
        usuarioRepository.save(usuario.get())
        return reseniaRepository.save(nuevaResenia)
    }
}
