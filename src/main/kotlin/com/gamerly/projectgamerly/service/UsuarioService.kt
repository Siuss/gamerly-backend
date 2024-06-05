package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    lateinit var usuarioRepository: UserRepository

    fun getUsuario(idUsuario: Long): UsuarioDetalleDTO {
        val usuario = usuarioRepository.findById(idUsuario).orElse(null)
            ?: throw Exception("Usuario con el id solicitado no existe");

        return UsuarioDetalleDTO(usuario)
    }

    fun login(credenciales: CredencialesDTO): UsuarioLoginDTO {
        val usuario = usuarioRepository.findByEmailAndPassword(credenciales.email, credenciales.password).orElse(null)
            ?: throw Exception("Credenciales incorrectas");

        return UsuarioLoginDTO.from(usuario)
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


    fun comentariosUsuario(idUsuario: Long) : List<ReseniasDTO> {
        val usuario = usuarioRepository.findById(idUsuario)
        val resenias = userRepository.findReseniasByUsuarioId(usuario.get().id)
        return resenias.map { resenia -> ReseniasDTO.fromResenias(usuario.get(), resenia) }
    }

    fun getAllUsers(): List<UsuarioDetalleDTO> {
        return usuarioRepository.findAll().map { UsuarioDetalleDTO(it) }

    fun deleteUsuario(idUsuario: Long): UsuarioDetalleDTO {
        val usuarioABorrar = getUsuario(idUsuario);

        usuarioRepository.deleteById(idUsuario);

        return usuarioABorrar;
    }
}
