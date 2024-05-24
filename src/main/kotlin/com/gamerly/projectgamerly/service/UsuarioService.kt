package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.dtos.UsuarioBusquedaDto
import com.gamerly.projectgamerly.repos.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioService {
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository
    fun busquedaAvanzada(juegosEnComun: List<String>?, puntaje: Long?): List<UsuarioBusquedaDto>{
        val usuariosFiltrados = usuarioRepository.busquedaAvanzada(juegosEnComun, puntaje)
        return usuariosFiltrados.map{usuario -> UsuarioBusquedaDto(usuario) }
    }
}
