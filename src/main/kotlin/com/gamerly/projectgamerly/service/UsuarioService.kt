package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.UserRepository
import jakarta.transaction.Transactional
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
            usuariosFiltrados = usuarioRepository.findUsuariosSegunFiltros(inputBusqueda.juegos?.toHashSet(), inputBusqueda.puntaje, diasHorarios)
        } else {
            usuariosFiltrados = usuarioRepository.findUsuariosSegunFiltros(inputBusqueda.juegos?.toHashSet(), inputBusqueda.puntaje, null)
        }
        return usuariosFiltrados.map{usuario -> UsuarioBusquedaDto(usuario) }
    }

    fun login(credenciales: CredencialesDTO): UsuarioLoginDTO {
        val usuario = usuarioRepository.findByEmailAndPassword(credenciales.email, credenciales.password).orElse(null)
            ?: throw Exception("Credenciales incorrectas");

        return UsuarioLoginDTO.from(usuario)
    }

    fun crearUsuario(user: UsuarioCreacionDTO): Usuario {
        val usuarioRegistro = Usuario().apply {
            nombre = user.nombre
            fechaDeNacimiento = user.fechaNacimiento
            email = user.email
            password = user.password
        }
        UsuarioCreacionDTO.fromUsuario(usuarioRegistro)
        return userRepository.save(usuarioRegistro)
    }

    fun editarUsuario(idUsuario: Long, usuarioEditado: UsuarioEditarData): UsuarioDetalleDTO {
        val usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")
            }
        usuarioEditado.nombre?.let { usuario.nombre = it }
        usuarioEditado.foto?.let { usuario.foto = it }
        usuarioEditado.nacionalidad?.let { usuario.nacionalidad = it }
        usuarioEditado.plataformas?.let { usuario.plataformas = it }
        return UsuarioDetalleDTO(usuarioRepository.save(usuario))
    }


    fun comentariosUsuario(idUsuario: Long): List<ReseniasDTO> {
        val usuario = usuarioRepository.findById(idUsuario)
        val resenias = userRepository.findReseniasByUsuarioId(usuario.get().id)
        return resenias.map { resenia -> ReseniasDTO.fromResenias(usuario.get(), resenia) }
    }

    fun getAllUsers(): List<UsuarioDetalleDTO> {
        return usuarioRepository.findAll().map { UsuarioDetalleDTO(it) }
    }

    fun deleteUsuario(idUsuario: Long): UsuarioDetalleDTO {
        val usuarioABorrar = getUsuario(idUsuario);
        usuarioRepository.deleteById(idUsuario);
        return usuarioABorrar;
    }


}
