package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.HorariosFavoritos
import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
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

    fun getUsuarioDetalle(idUsuario: Long): UsuarioDetalleDTO {
        val usuario = usuarioRepository.findById(idUsuario).orElse(null)
            ?: throw Exception("Usuario con el id solicitado no existe");
        val primerResenia = conversionReseniaDTO(usuario.resenias.first())

        return UsuarioDetalleDTO(usuario, primerResenia)
    }

    fun busquedaAvanzada(inputBusqueda: InputBusquedaDTO): List<UsuarioBusquedaDto>{
        //TODO: cambiar juegos a lista de Int, resolver segun id
        val diasEnum = inputBusqueda.dias?.map { DiaDeLaSemana.valueOf(it.uppercase()) }
        val horariosEnum = inputBusqueda.horarios?.map { HorariosFavoritos.valueOf(it.uppercase()) }
        val usuariosFiltrados = usuarioRepository.findUsuariosSegunFiltros(
            inputBusqueda.puntaje,
            diasEnum,
            horariosEnum
        )
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


    fun comentariosUsuario(idUsuario: Long): List<ReseniasDTO> {
        val usuarioReceptor = usuarioRepository.findById(idUsuario).get()
        val reseniasDTO = mutableListOf<ReseniasDTO>()
        usuarioReceptor.resenias.forEach {
            reseniasDTO.add(conversionReseniaDTO(it))
        }
        return reseniasDTO
    }

    fun conversionReseniaDTO(resenia: Resenia): ReseniasDTO {
        val usuarioEmisor = usuarioRepository.findById(resenia.idUsuarioEmisor).get()
        return ReseniasDTO.fromResenias(usuarioEmisor, resenia)
    }

    fun getAllUsers(): List<UsuarioDetalleDTO> {
        val usuarios = usuarioRepository.findAll()
        val usuariosDTO = mutableListOf<UsuarioDetalleDTO>()
        usuarios.forEach {
            val primerResenia = conversionReseniaDTO(it.resenias.first())
            usuariosDTO.add(UsuarioDetalleDTO(it, primerResenia))
        }
        return usuariosDTO
    }

    fun deleteUsuario(idUsuario: Long): UsuarioDetalleDTO {
        val usuarioABorrar = getUsuarioDetalle(idUsuario);
        usuarioRepository.deleteById(idUsuario);
        return usuarioABorrar;
    }


}
