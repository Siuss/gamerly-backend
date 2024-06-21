package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.HorariosFavoritos
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import com.gamerly.projectgamerly.utilities.PasswordMismatch
import com.gamerly.projectgamerly.utilities.userNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
            camposVacios()
        }
        val usuario = usuarioRepository.findByEmailAndPassword(usuarioCrendecial.email, usuarioCrendecial.password)
        if (!usuario.isPresent) {
            throw userNotFound("Credenciales incorrectas")
        }

        return UsuarioLoginDTO.from( usuario.get());
    }
    fun crearUsuario(user: UsuarioCreacionDTO): Usuario {
        val usuarioRegistro = Usuario().apply {
            nombre = user.nombre
            fechaDeNacimiento = LocalDate.parse(
                user.fechaNacimiento,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
            email = user.email
            password = user.password
        }
        return userRepository.save(usuarioRegistro)
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

    fun getUsuarioPorJuego(idJuego: Long): List<UsuarioDetalleDTO> {
        val usuarios = usuarioRepository.findAllByjuegosPreferidos_Id(idJuego)
            if(usuarios.isEmpty()){
                throw Exception("No existen jugadores que jueguen al juego con el id solicitado");

            }
        return usuarios.map{UsuarioDetalleDTO(it)}
    }
}
