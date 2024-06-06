package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.InputBusquedaDTO
import com.gamerly.projectgamerly.dtos.UsuarioBusquedaDto
import com.gamerly.projectgamerly.dtos.UsuarioCreacionDTO
import com.gamerly.projectgamerly.dtos.UsuarioDetalleDTO
import com.gamerly.projectgamerly.repos.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    lateinit var usuarioRepository: UserRepository

    fun getUsuario(idUsuario: Long): UsuarioDetalleDTO {
        val usuario = usuarioRepository.findById(idUsuario).get()
        return UsuarioDetalleDTO(usuario)
    }

    fun busquedaAvanzada(inputBusqueda: InputBusquedaDTO): List<UsuarioBusquedaDto>{
        //TODO: cambiar juegos a lista de Int, resolver segun id
        val diasHorarios = mutableListOf<String>()
        inputBusqueda.dias?.forEach{ dia ->
            inputBusqueda.horarios?.forEach { horario ->
                diasHorarios.add(dia+horario)
                println(dia+horario)
            }
        }
        var usuariosFiltrados = listOf<Usuario>()
        if (diasHorarios.isNotEmpty()) {
            usuariosFiltrados = usuarioRepository.findUsuariosSegunFiltros(inputBusqueda.juegos, inputBusqueda.puntaje, diasHorarios)
        } else {
            usuariosFiltrados = usuarioRepository.findUsuariosSegunFiltros(inputBusqueda.juegos, inputBusqueda.puntaje, null)
        }
        return usuariosFiltrados.map{usuario -> UsuarioBusquedaDto(usuario) }
    }

    fun crearUsuario(user: UsuarioCreacionDTO) : Usuario  {
        val usuarioRegistro = Usuario().apply {
            nombre = user.nombre
            fechaDeNacimiento = user.fechaNacimiento
            email = user.email
            password = user.password
        }
        UsuarioCreacionDTO.fromUsuario(usuarioRegistro)
        return userRepository.save(usuarioRegistro)
    }
}
