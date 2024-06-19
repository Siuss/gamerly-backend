package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.utilities.PasswordMismatch
import com.gamerly.projectgamerly.utilities.userNotFound
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
        val usuarioCrendecial = Usuario().apply {
            email = credenciales.email
            password = credenciales.password
            camposValidos()
        }
        val usuario = usuarioRepository.findByEmail(usuarioCrendecial.email)
        if (usuario.isPresent) {
            val usuarioEncontrado = usuario.get()
            if (usuarioEncontrado.password == usuarioCrendecial.password) {
                return UsuarioLoginDTO.from(usuarioEncontrado);
            } else {
                throw PasswordMismatch("Contraseña incorrecta")
            }
        } else {
            throw userNotFound("Usuario no encontrado")
        }
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

    fun agregarAmigo(idUsuario: Long, idAmigo: Long): UsuarioDetalleDTO {
        val usuario = usuarioRepository.findById(idUsuario).orElseThrow { Exception("Usuario con el id $idUsuario no encontrado") }
        val amigo = usuarioRepository.findById(idAmigo).orElseThrow { Exception("Usuario con el id $idAmigo no encontrado") }

        if (usuario.id == amigo.id) {
            throw Exception("Un usuario no puede agregarse a sí mismo como amigo")
        }

        if (!usuario.amigos.contains(amigo)) {
            usuario.amigos.add(amigo)
            amigo.amigos.add(usuario)
        }

        usuarioRepository.save(usuario)
        usuarioRepository.save(amigo)

        return UsuarioDetalleDTO(usuario)  // Se asegura de retornar el DTO
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
