package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.UsuarioBusquedaDto
import com.gamerly.projectgamerly.dtos.UsuarioCreacionDTO
import com.gamerly.projectgamerly.dtos.UsuarioDetalleDTO
import com.gamerly.projectgamerly.repos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    lateinit var usuarioRepository: UserRepository

    fun getUsuario(idUsuario: Long): UsuarioDetalleDTO {
        val usuario = usuarioRepository.findById(idUsuario).orElse(null)
            ?: throw Exception("Usuario con el id solicitado no existe")

        return UsuarioDetalleDTO(usuario)
    }

//    fun busquedaAvanzada(juegosEnComun: List<String>?, puntaje: Long?): List<UsuarioBusquedaDto>{
//        val usuariosFiltrados = usuarioRepository.busquedaAvanzada(juegosEnComun, puntaje)
//        return usuariosFiltrados.map{usuario -> UsuarioBusquedaDto(usuario) }
//    }

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

    fun deleteUsuario(idUsuario: Long): UsuarioDetalleDTO {
        val usuarioABorrar = usuarioRepository.findById(idUsuario).orElse(null)
            ?: throw Exception("Usuario con id solicitado no existe");

        usuarioRepository.deleteById(idUsuario);

        return UsuarioDetalleDTO(usuarioABorrar);
    }
}
