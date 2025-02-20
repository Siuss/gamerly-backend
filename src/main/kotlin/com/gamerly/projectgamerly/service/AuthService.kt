package com.gamerly.projectgamerly.service


import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.AuthDTO
import com.gamerly.projectgamerly.dtos.UsuarioCreacionDTO
import com.gamerly.projectgamerly.exceptions.CredencialesInvalidasException
import com.gamerly.projectgamerly.exceptions.NotFoundException
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.utilities.InvalidEmail
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/*
Se crea un nuevo service para iniciar sesion y crear usuario*/
@Service
class AuthService : UserDetailsService {

    val logger = LoggerFactory.getLogger(UsuarioService::class.java)

    @Autowired lateinit var usuarioRepository : UserRepository


    fun validarUsuario(emailUser: String) = usuarioRepository.findByEmail(emailUser).orElseThrow { CredencialesInvalidasException() }


    @Transactional(Transactional.TxType.REQUIRED)
    fun login( authData : AuthDTO) : Usuario {
        val usuarioData = validarUsuario(authData.email)
        usuarioData.loguearse()
        usuarioData.validarCredenciales(authData.password)
        return usuarioData
    }


    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    fun verUsuario(nombreUsuario: String) = usuarioRepository.findByEmail(nombreUsuario).orElseThrow { NotFoundException("No se encontró el usuario con el nombre $nombreUsuario") }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) throw CredencialesInvalidasException()
        val usuario = verUsuario(username)
        logger.info("Usuario " + usuario.nombre + " encontrado")
        return User(usuario.nombre, usuario.password, emptyList())
    }

    // El efecto que tiene es simplemente devolver un ok si el filtro de JWT (JWTAuthorizationFilter) pasa
    fun validar(): String = "ok"

    @Transactional(Transactional.TxType.REQUIRED)
    fun crearUsuario(user: UsuarioCreacionDTO): Usuario {
        if (usuarioRepository.findByEmail(user.email).isPresent) throw InvalidEmail("El email ${user.email} ya está en uso")

        val usuarioRegistro = Usuario().apply {
            nombre = user.nombre
            fechaDeNacimiento = LocalDate.parse(
                user.fechaNacimiento,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
            email = user.email
            crearPassword(user.password)
            discord = user.discord
            nacionalidad = user.nacionalidad
            fileName = "https://i.ibb.co/HG1GTNR/avatar.png"
        }
        usuarioRepository.save(usuarioRegistro)
        return usuarioRegistro
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun loginGoogle(user: UsuarioCreacionDTO): Usuario {
        if (usuarioRepository.findByEmail(user.email).isPresent) {
            return login(AuthDTO(user.email, user.password))
        } else {
            crearUsuario(user)
            return login(AuthDTO(user.email, user.password))
        }
    }

}