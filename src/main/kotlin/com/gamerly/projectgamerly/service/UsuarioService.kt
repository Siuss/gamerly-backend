package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.*
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.GameRepository
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.utilities.CredencialesInvalidas
import com.gamerly.projectgamerly.utilities.InvalidEmail
import com.gamerly.projectgamerly.utilities.userNotFound
import com.gamerly.projectgamerly.utils.UserNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class UsuarioService {
    @Autowired
    lateinit var usuarioRepository: UserRepository
    @Autowired
    lateinit var juegoRepository: GameRepository
    @Autowired
    lateinit var notificacionService: NotificacionService
    @Autowired
    lateinit var emailService: EmailService

    fun conversionReseniaDTO(resenia: Resenia): ReseniasDTO {
        val usuarioEmisor = usuarioRepository.findById(resenia.idUsuarioEmisor).get()
        return ReseniasDTO.fromResenias(usuarioEmisor, resenia)
    }

    fun getUsuario(idUsuario: Long): Usuario {
        val usuario = usuarioRepository.findById(idUsuario).orElse(null)
            ?: throw Exception("Usuario con el id solicitado no existe");
        return usuario
    }

    fun getUsuarioConSolicitudes(idUsuario: Long): Usuario {
        val usuario = usuarioRepository.findSolicitudesById(idUsuario).orElse(null)
            ?: throw Exception("Usuario con el id solicitado no existe");
        return usuario
    }

    fun getUsuarioPorEmail(email: String): Usuario {
        val usuario = usuarioRepository.findByEmail(email).orElse(null)
            ?: throw Exception("Usuario con el email solicitado no existe");

        return usuario
    }

    fun getUsuarioPorTokenRecuperacion(token: String): Usuario {
        val usuario = usuarioRepository.findByTokenRecuperacion(token).orElse(null)
            ?: throw UserNotFound("Usuario con ese token de recuperacion no existe");

        if(usuario.fechaRecuperacionClave == null){
            throw UserNotFound("Usuario con ese token de recuperacion no existe")
        }

        val horarioMaximoValidez = usuario.fechaRecuperacionClave?.plusMinutes(5)
        val presente = LocalDateTime.now()

        if(presente.isAfter(horarioMaximoValidez)){
            throw UserNotFound("Han pasado mas de 5 minutos y el token ha expirado")
        }

        return usuario
    }

    fun busquedaAvanzada(inputBusqueda: InputBusquedaDTO, idJuego: Long ): List<UsuarioBusquedaDto>{

        val usuarios = usuarioRepository.findUsuariosSegunFiltros(
            inputBusqueda.resenia,
            inputBusqueda.dias,
            inputBusqueda.momentos,
            inputBusqueda.nombre,
        )

        val usuarioConDetalle = usuarios.map {getUsuario(it.id)}

        val usuariosFiltrados = usuarioConDetalle.filter{usuario -> usuario.juegosPreferidos.any { juego -> juego.id == idJuego }}

        return usuariosFiltrados.map{usuario -> UsuarioBusquedaDto(usuario) }
    }

//    fun login(credenciales: CredencialesDTO): UsuarioLoginDTO {
//        val usuarioCrendecial = Usuario().apply {
//            email = credenciales.email
//            password = credenciales.password
//            tokenNotificaciones = credenciales.tokenNotificaciones
//        }
//        val usuario = usuarioRepository.findByEmail(usuarioCrendecial.email)
//
//        if (usuario.isEmpty) {
//            throw CredencialesInvalidas("Credenciales invalidas")
//        }
//
//        val usuarioEncontrado = usuario.get()
//
//        if (usuarioEncontrado.password != usuarioCrendecial.password) {
//            throw CredencialesInvalidas("Credenciales invalidas")
//        }
//
//        usuarioEncontrado.tokenNotificaciones = credenciales.tokenNotificaciones
//        usuarioRepository.save(usuarioEncontrado)
//
//        return UsuarioLoginDTO.from(usuarioEncontrado);
//    }

//    fun crearUsuario(user: UsuarioCreacionDTO): Usuario {
//        val usuario = usuarioRepository.findByEmail(user.email).orElse(null)
//
//        if(usuario != null){
//            throw InvalidEmail("Ya existe un usuario registrado con ese email")
//        }
//
//        val usuarioRegistro = Usuario().apply {
//            nombre = user.nombre
//            fechaDeNacimiento = LocalDate.parse(
//                user.fechaNacimiento,
//                DateTimeFormatter.ofPattern("dd/MM/yyyy")
//            )
//            email = user.email
//            password = user.password
//            discord = user.discord
//            nacionalidad = user.nacionalidad
//            foto = "https://i.ibb.co/HG1GTNR/avatar.png"
//
//        }
//        return usuarioRepository.save(usuarioRegistro)
//    }

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

        val reseniasDto = usuario.resenias.map{conversionReseniaDTO(it)}
        return UsuarioDetalleDTO(usuarioRepository.save(usuario), reseniasDto)
    }

    fun actualizarPerfil(perfil: UsuarioDetalleEdicionDTO): Usuario{
            val usuario = getUsuario(perfil.id)

            perfil.nombre?.let { usuario.nombre = it }
            perfil?.foto?.let { usuario.foto = it }
            perfil?.nacionalidad?.let { usuario.nacionalidad = it }
            perfil?.discord?.let { usuario.discord = it }

            if (perfil?.fechaDeNacimiento != null) {
                val fechaNacimiento = LocalDate.parse(
                    perfil.fechaDeNacimiento.toString(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                )
                fechaNacimiento.let { usuario.fechaDeNacimiento = it }
            }

            if (perfil.juegosPreferidos != null) {
                val juegos = perfil.juegosPreferidos!!.map { juegoRepository.findJuegoByNombre(it) }.toMutableSet()
                juegos.let { usuario.juegosPreferidos = it }
            }

            if (perfil.plataformas != null) {
                val plataformas = perfil.plataformas!!.map {
                    Plataformas.valueOf(it.uppercase().replace(" ", ""))
                }.toSet()
                plataformas.let { usuario.plataformas = it }
            }

        if (perfil.diasHorariosPreferidos != null) {
            val diasHorariosPreferidos = perfil.diasHorariosPreferidos.toMutableSet()
            diasHorariosPreferidos.let { usuario.diasHorariosPreferidos = it }
        }

            return usuarioRepository.save(usuario)
    }

    fun comentariosUsuario(idUsuario: Long): List<ReseniasDTO> {
        val usuarioReceptor = usuarioRepository.findById(idUsuario).get()
        val reseniasDTO = mutableListOf<ReseniasDTO>()
        usuarioReceptor.resenias.forEach {
            reseniasDTO.add(conversionReseniaDTO(it))
        }
        return reseniasDTO
    }

    fun getAllUsers(): List<UsuarioDetalleDTO> {
        val usuarios = usuarioRepository.findAll()
        val usuariosDTO = mutableListOf<UsuarioDetalleDTO>()
        usuarios.forEach {
            val reseniasDto = it.resenias.map{conversionReseniaDTO(it)}
            usuariosDTO.add(UsuarioDetalleDTO(it, reseniasDto))
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

    fun getAmigosDelUsuario(idUsuario: Long, traerBloqueados: Boolean): List<AmigoDTO> {
        val usuario = getUsuario(idUsuario)
        val amigos = usuario.amigos.map{AmigoDTO.from(getUsuario(it.id))}

        if(!traerBloqueados){
            return amigos
        }
        val bloqueados = usuario.bloqueados.map{AmigoDTO.from(getUsuario(it.id)).also{ dto -> dto.bloqueado=true }}
        val amigosYBloqueados = amigos + bloqueados

        return amigosYBloqueados
    }

    fun deleteAmigoDelUsuario(idUsuario: Long, idAmigo: Long): Usuario{
        val usuario = getUsuario(idUsuario)
        val amigo = getUsuario(idAmigo)

        if(usuario.amigos.all { it.id != idAmigo }){
            return usuario
        }


        usuario.amigos = usuario.amigos.filter{ it.id != amigo.id }.toMutableSet()
        amigo.amigos = usuario.amigos.filter{ it.id != usuario.id }.toMutableSet()

        usuarioRepository.save(usuario)
        usuarioRepository.save(amigo)

        val notificacion = Notificacion(amigo.tokenNotificaciones, "${usuario.nombre} y tu ya no son amigos")
        notificacionService.enviarNotificacion(notificacion)

        return amigo
    }

    fun solicitarClave(email: String): String{
        val usuario = getUsuarioPorEmail(email)

        val number: Int = Random().nextInt(999999)
        val tokenRecuperacion = String.format("%06d", number)

        usuario.tokenRecuperacion = tokenRecuperacion
        usuario.fechaRecuperacionClave = LocalDateTime.now()

        usuarioRepository.save(usuario)

        emailService.enviarMail(email,
            "Gamerly - Se ha solicitado una renovacion de contraseña",
            "En caso que no seas quien la ha solicitado por favor desestima este mensaje.\r\n\r\nTu codigo de verificacion es: $tokenRecuperacion"
        )

        return email
    }

    fun verificarTokenDeRecuperacion(token: String): Usuario {
        val usuario = getUsuarioPorTokenRecuperacion(token)
        usuario.tokenRecuperacion = null
        usuario.fechaRecuperacionClave = null
        return usuario
    }

    fun nuevaClave(email: String, contrasenia: String): Usuario{
        val usuario = getUsuarioPorEmail(email)
        usuario.password = contrasenia
        usuarioRepository.save(usuario)

        return usuario
    }
}
