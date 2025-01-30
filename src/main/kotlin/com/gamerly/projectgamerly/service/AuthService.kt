package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.dtos.AuthDTO
import com.gamerly.projectgamerly.exceptions.CredencialesInvalidasException
import com.gamerly.projectgamerly.exceptions.NotFoundException
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.security.TokenUtils
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService : UserDetailsService {

    val logger = LoggerFactory.getLogger(UsuarioService::class.java)

    @Autowired lateinit var usuarioRepository : UserRepository


    fun validarUsuario(emailUser: String) = usuarioRepository.findByEmail(emailUser).orElseThrow { CredencialesInvalidasException() }


    @Transactional(Transactional.TxType.REQUIRED)
    fun login( authData : AuthDTO) {
        val usuarioData = validarUsuario(authData.email)
        usuarioData.loguearse()
        usuarioData.validarCredenciales(authData.password)

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

}