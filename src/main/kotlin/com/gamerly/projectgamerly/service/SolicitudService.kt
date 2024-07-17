package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.HorariosFavoritos
import com.gamerly.projectgamerly.domain.Notificacion
import com.gamerly.projectgamerly.domain.Solicitud
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.SolicitudRepository
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import com.gamerly.projectgamerly.utilities.NoSePuedeAgregarComoAmigoASiMismo
import com.gamerly.projectgamerly.utilities.userNotFound
import com.gamerly.projectgamerly.utils.Ruta
import com.gamerly.projectgamerly.utils.SolicitudNotFound
import com.gamerly.projectgamerly.utils.TipoNotificacion
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import io.github.jav.exposerversdk.examples.ExampleExpoServer
@Service
class SolicitudService {
    @Autowired
    private lateinit var notificacionService: NotificacionService

    @Autowired
    private lateinit var solicitudRepository: SolicitudRepository

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var usuarioRepository: UserRepository

    @Transactional
    fun crearSolicitudDeAmistad(idCreador: Long, idAmigo: Long, mensaje: String){
        if(idCreador == idAmigo) {
            throw NoSePuedeAgregarComoAmigoASiMismo("Un usuario no puede agregarse como amigo a si ismo")
        }
         
        val usuarioCreador = usuarioService.getUsuario(idCreador)
        val usuarioAmigo = usuarioService.getUsuario(idAmigo)

        if (usuarioCreador.amigos.contains(usuarioAmigo)) return

        val nuevaSolicitud = Solicitud(0, usuarioCreador, usuarioAmigo, mensaje)
        solicitudRepository.save(nuevaSolicitud)

        usuarioCreador.solicitudesEnviadas.add(nuevaSolicitud)
        usuarioRepository.save(usuarioCreador)

        // Si el usuario esta shadowbaneado no se envia la solicitud pero la peticion
        // finaliza satisfactoriamente, para generar la ilusion de interaccion
        if(usuarioCreador.shadowBan == true){
            return
        }

        usuarioAmigo.solicitudesRecibidas.add(nuevaSolicitud)
        usuarioRepository.save(usuarioAmigo)

        val dataNotificacion: MutableMap<String, Any> = mutableMapOf("ruta" to Ruta.SOLICITUDES_PENDIENTES, "tipo" to TipoNotificacion.NUEVA_SOLICITUD_AMISTAD)

        val notificacion = Notificacion(usuarioAmigo.tokenNotificaciones, "${usuarioCreador.nombre} te ha enviado una solicitud de amistad", "$mensaje\n\nDiscord: ${usuarioCreador.discord}").apply {
            data = dataNotificacion
        }

        notificacionService.enviarNotificacion(notificacion)
    }

    fun tieneSolicitudDeAmistadPendiente(idCreador: Long, idAmigo: Long): Solicitud {
        val solicitud = solicitudRepository.findByUsuarioCreador_IdAndUsuarioReceptor_Id(idCreador, idAmigo)

        if(solicitud.isEmpty){
            throw SolicitudNotFound("No se encontro la solicitud pendiente")
        }

        return solicitud.get()
    }

    fun getSolicitudesPendientes(idUsuario: Long): List<Solicitud> {
        val usuario = usuarioService.getUsuarioConSolicitudes(idUsuario)
        return usuario.solicitudesRecibidas.toList()
    }

    @Transactional
    fun aceptarSolicitud(idSolicitud: Long): Solicitud {
        val solicitud = solicitudRepository.findById(idSolicitud)

        if(solicitud.isEmpty){
            throw SolicitudNotFound("No se encontro la solicitud")
        }

        val solicitudEncontrada = solicitud.get()

        val usuarioCreador = solicitudEncontrada.usuarioCreador
        val usuarioReceptor = solicitudEncontrada.usuarioReceptor

        usuarioCreador.solicitudesEnviadas.removeIf{it.id == solicitudEncontrada.id}
        usuarioReceptor.solicitudesRecibidas.removeIf{it.id == solicitudEncontrada.id}

        usuarioCreador.amigos.add(usuarioReceptor)
        usuarioReceptor.amigos.add(usuarioCreador)

        usuarioRepository.save(usuarioCreador)
        usuarioRepository.save(usuarioReceptor)

        solicitudRepository.delete(solicitudEncontrada)

        val dataNotificacion: MutableMap<String, Any> = mutableMapOf("ruta" to Ruta.AMIGOS, "tipo" to TipoNotificacion.SOLICITUD_AMISTAD_ACEPTADA)

        val notificacion = Notificacion(usuarioCreador.tokenNotificaciones, "${usuarioReceptor.nombre} ha aceptado tu solicitud de amistad").apply{
            data = dataNotificacion
        }
        notificacionService.enviarNotificacion(notificacion)

        return solicitudEncontrada
    }

    @Transactional
    fun rechazarSolicitud(idSolicitud: Long): Solicitud {
        val solicitud = solicitudRepository.findById(idSolicitud)

        if(solicitud.isEmpty){
            throw SolicitudNotFound("No se encontro la solicitud")
        }

        val solicitudEncontrada = solicitud.get()

        val usuarioCreador = solicitudEncontrada.usuarioCreador
        val usuarioReceptor = solicitudEncontrada.usuarioReceptor

        usuarioCreador.solicitudesEnviadas.removeIf{it.id == solicitudEncontrada.id}
        usuarioReceptor.solicitudesRecibidas.removeIf{it.id == solicitudEncontrada.id}

        usuarioRepository.save(usuarioCreador)
        usuarioRepository.save(usuarioReceptor)

        solicitudRepository.delete(solicitudEncontrada)

        val dataNotificacion: MutableMap<String, Any> = mutableMapOf("ruta" to Ruta.AMIGOS, "tipo" to TipoNotificacion.SOLICITUD_AMISTAD_RECHAZADA)

        val notificacion = Notificacion(usuarioCreador.tokenNotificaciones, "${usuarioCreador.nombre} ha rechazado tu solicitud de amistad").apply {
            data = dataNotificacion
        }
        notificacionService.enviarNotificacion(notificacion)

        return solicitudEncontrada
    }
}
