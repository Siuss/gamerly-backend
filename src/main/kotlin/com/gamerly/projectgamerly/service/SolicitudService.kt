package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.HorariosFavoritos
import com.gamerly.projectgamerly.domain.Solicitud
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.SolicitudRepository
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import com.gamerly.projectgamerly.utilities.userNotFound
import com.gamerly.projectgamerly.utils.SolicitudNotFound
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class SolicitudService {
    @Autowired
    private lateinit var solicitudRepository: SolicitudRepository

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var usuarioRepository: UserRepository

    @Transactional
    fun crearSolicitudDeAmistad(idCreador: Long, idAmigo: Long, mensaje: String){
        if(idCreador === idAmigo) {
            //TODO: Tirar excepcion
        }

         
        val usuarioCreador = usuarioService.getUsuario(idCreador)
        val usuarioAmigo = usuarioService.getUsuario(idAmigo)

        if (usuarioCreador.amigos.contains(usuarioAmigo)) return

        val nuevaSolicitud = Solicitud(0, usuarioCreador, usuarioAmigo, mensaje)
        solicitudRepository.save(nuevaSolicitud)

        usuarioCreador.solicitudesEnviadas.add(nuevaSolicitud)
        usuarioRepository.save(usuarioCreador)

        usuarioAmigo.solicitudesRecibidas.add(nuevaSolicitud)
        usuarioRepository.save(usuarioAmigo)
    }

    fun tieneSolicitudDeAmistadPendiente(idCreador: Long, idAmigo: Long): Solicitud {
        val solicitud = solicitudRepository.findByUsuarioCreador_IdAndUsuarioReceptor_Id(idCreador, idAmigo)

        if(solicitud.isEmpty){
            throw SolicitudNotFound("No se encontro la solicitud pendiente")
        }

        return solicitud.get()
    }

    fun getSolicitudesPendientes(idUsuario: Long): List<Solicitud> {
        return solicitudRepository.findByUsuarioReceptor_Id(idUsuario)
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

        // TODO: Mandar notificaciones push a los usuarios

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

        // TODO: Mandar notificaciones push a los usuarios

        return solicitudEncontrada
    }
}
