package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.HorariosFavoritos
import com.gamerly.projectgamerly.domain.Plataformas
import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.dtos.UsuarioBusquedaDto
import com.gamerly.projectgamerly.repos.GameRepository
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import com.gamerly.projectgamerly.utilities.PasswordMismatch
import com.gamerly.projectgamerly.utilities.userNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.springframework.transaction.annotation.Transactional
import org.hibernate.Hibernate

@Service
class UsuarioService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    lateinit var usuarioRepository: UserRepository
    @Autowired
    lateinit var juegoRepository: GameRepository

    fun conversionReseniaDTO(resenia: Resenia): ReseniasDTO {
        val usuarioEmisor = usuarioRepository.findById(resenia.idUsuarioEmisor).get()
        return ReseniasDTO.fromResenias(usuarioEmisor, resenia)
    }

    fun getUsuario(idUsuario: Long): Usuario {
        val usuario = usuarioRepository.findById(idUsuario).orElse(null)
            ?: throw Exception("Usuario con el id solicitado no existe");

        return usuario
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
            //FIXME: camposValidos() cuando te intentas loguear con la contrasenia 123 esta validacion falla
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
            fechaDeNacimiento = LocalDate.parse(
                user.fechaNacimiento,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
            email = user.email
            password = user.password
            discord = user.discord
        }
        return userRepository.save(usuarioRegistro)
    }

    fun editarUsuario(idUsuario: Long, usuarioEditado: UsuarioEditarDTO): UsuarioDetalleDTO {
        val usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow {
                userNotFound("Usuario con el id solicitado no existe")
            }

        usuarioEditado.nombre?.let { usuario.nombre = it }
        usuarioEditado.foto?.let { usuario.foto = it }
        usuarioEditado.nacionalidad?.let { usuario.nacionalidad = it }

        if (usuarioEditado.fechaNacimiento != null) {
            val fechaNacimiento = LocalDate.parse(
                usuarioEditado.fechaNacimiento,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
            fechaNacimiento.let { usuario.fechaDeNacimiento = it }
        }

        if (usuarioEditado.juegos != null) {
            val juegos = usuarioEditado.juegos!!.map { juegoRepository.findJuegoByNombre(it) }.toMutableSet()
            juegos.let { usuario.juegosPreferidos = it }
        }

        if (usuarioEditado.plataformas != null) {
            val plataformas = usuarioEditado.plataformas!!.map {
                Plataformas.valueOf(it.uppercase().replace(" ", ""))
            }.toSet()
            plataformas.let { usuario.plataformas = it }//dale
        }

        val primerResenia = conversionReseniaDTO(usuario.resenias.first())
        return UsuarioDetalleDTO(usuarioRepository.save(usuario), primerResenia)
    }


    fun comentariosUsuario(idUsuario: Long): List<ReseniasDTO> {
        val usuarioReceptor = usuarioRepository.findById(idUsuario).get()
        val reseniasDTO = mutableListOf<ReseniasDTO>()
        usuarioReceptor.resenias.forEach {
            reseniasDTO.add(conversionReseniaDTO(it))
        }
        return reseniasDTO
    }

//hice el pull

    fun getAllUsers(): List<UsuarioDetalleDTO> {
        val usuarios = usuarioRepository.findAll()
        val usuariosDTO = mutableListOf<UsuarioDetalleDTO>()
        usuarios.forEach {
            val primerResenia = conversionReseniaDTO(it.resenias.first())
            usuariosDTO.add(UsuarioDetalleDTO(it, primerResenia))
        }
        return usuariosDTO
    }

    fun deleteUsuario(idUsuario: Long): Usuario {
        val usuarioABorrar = getUsuario(idUsuario);
        usuarioRepository.deleteById(idUsuario);
        return usuarioABorrar;
    }

    fun getUsuarioPorJuego(idJuego: Long): List<UsuarioBusquedaJuegosDTO> {
        val usuarios = usuarioRepository.findAllByjuegosPreferidos_Id(idJuego)
            if(usuarios.isEmpty()){
                throw userNotFound("No existen jugadores que jueguen al juego con el id solicitado");

            }
        return usuarios.map{UsuarioBusquedaJuegosDTO(it)}
    }

    fun getAmigosDelUsuario(idUsuario: Long): List<Usuario> {
        val usuario = getUsuario(idUsuario)

        return usuario.amigos.map{getUsuario(it.id)}
    }

    fun deleteAmigoDelUsuario(idUsuario: Long, idAmigo: Long): Usuario{
        val usuario = getUsuario(idUsuario)
        val amigo = getUsuario(idAmigo)

        usuario.amigos = usuario.amigos.filter{ it.id != amigo.id }.toMutableSet()
        amigo.amigos = usuario.amigos.filter{ it.id != usuario.id }.toMutableSet()

        usuarioRepository.save(usuario)
        usuarioRepository.save(amigo)

        return amigo
    }
}
