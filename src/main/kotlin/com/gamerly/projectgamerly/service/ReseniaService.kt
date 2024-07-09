package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Notificacion
import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.repos.ReviewRepository
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.utilities.ReseniaException
import com.gamerly.projectgamerly.utils.ReseniaPendienteNotFound
import com.gamerly.projectgamerly.utils.Ruta
import com.gamerly.projectgamerly.utils.TipoNotificacion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime

@Service
class ReseniaService() {
    @Autowired
    private lateinit var usuarioService: UsuarioService
    @Autowired lateinit var reseniaRepository : ReviewRepository
    @Autowired lateinit var usuarioRepository : UserRepository
    @Autowired lateinit var notificacionService : NotificacionService


    @Transactional()
    fun crearResenia(reseniaBody: ReseniaCreacionDTO, idUsuarioEmisor : Long, idUsuarioReceptor : Long): Resenia {
        if (idUsuarioReceptor == idUsuarioEmisor) {
            throw ReseniaException("No se puede dejar una reseña a si mismo")
        }
        val usuarioCreador = usuarioRepository.findById(idUsuarioEmisor).get()
        val usuarioReceptor = usuarioRepository.findById(idUsuarioReceptor).get()
        val existingResenia = usuarioReceptor.resenias.find { it.idUsuarioEmisor == idUsuarioEmisor }
        if (existingResenia != null) {
            throw ReseniaException("Ya has dejado una reseña a este usuario")
        }
        val nuevaResenia = Resenia(
            idUsuarioEmisor,
            reseniaBody.puntaje,
            reseniaBody.comentario,
            LocalDate.now(),
            LocalTime.now()
        )
        usuarioReceptor.addReseniaPendiente(nuevaResenia)
        usuarioRepository.save(usuarioReceptor)

        val dataNotificacion: MutableMap<String, Any> = mutableMapOf("ruta" to Ruta.RESENIAS_PENDIENTES, "tipo" to TipoNotificacion.NUEVA_SOLICITUD_AMISTAD)
        val notificacion = Notificacion(usuarioReceptor.tokenNotificaciones, "${usuarioCreador.nombre} desea dejarte una reseña", "Es cierto que jugaron juntos?").apply{
            data = dataNotificacion
        }
        notificacionService.enviarNotificacion(notificacion)
        return reseniaRepository.save(nuevaResenia)
    }

    fun getResenias(idUsuario: Long): MutableSet<Resenia> {
        val usuario = usuarioService.getUsuario(idUsuario)
        return usuario.resenias
    }

    fun getReseniasPendientes(idUsuario: Long): MutableSet<Resenia> {
        val usuario = usuarioService.getUsuario(idUsuario)
        return usuario.reseniasPendientes
    }

    fun tieneReseniaDe(idUsuarioReceptor: Long, idUsuarioCreador: Long): Boolean {
        val usuarioReceptor = usuarioService.getUsuario(idUsuarioReceptor)
        return usuarioReceptor.resenias.any{it.idUsuarioEmisor == idUsuarioCreador}
    }

    @Transactional
    fun aceptarReseniaPendiente(idResenia: Long, idUsuarioLogueado: Long): Resenia{
        val usuario = usuarioService.getUsuario(idUsuarioLogueado)
        val reseniaPendiente = usuario.reseniasPendientes.find{resenia -> resenia.id == idResenia}

        if(reseniaPendiente == null){
            throw ReseniaPendienteNotFound("Reseña con el id solicitado no existe")
        }

        val resenia = reseniaRepository.findById(idResenia)

        usuario.removeReseniaPendienteById(idResenia)
        usuario.addResenia(resenia.get())

        val usuarioCreador = usuarioService.getUsuario(reseniaPendiente.idUsuarioEmisor)

        val dataNotificacion: MutableMap<String, Any> = mutableMapOf("ruta" to Ruta.MI_PERFIL, "tipo" to TipoNotificacion.ACEPTAR_RESENIA)

        val notificacion = Notificacion(usuarioCreador.tokenNotificaciones, "${usuario.nombre} ha aceptado tu reseña").apply {
            data = dataNotificacion
        }
        notificacionService.enviarNotificacion(notificacion)

        return resenia.get()
    }

    @Transactional
    fun rechazarReseniaPendiente(idResenia: Long, idUsuarioLogueado: Long): Resenia{
        val usuario = usuarioService.getUsuario(idUsuarioLogueado)
        val reseniaPendiente = usuario.reseniasPendientes.find{resenia -> resenia.id == idResenia}

        if(reseniaPendiente == null){
            throw ReseniaPendienteNotFound("Reseña con el id solicitado no existe")
        }

        val resenia = reseniaRepository.findById(idResenia)
        usuario.removeReseniaPendienteById(idResenia)

        val usuarioCreador = usuarioService.getUsuario(reseniaPendiente.idUsuarioEmisor)

        val dataNotificacion: MutableMap<String, Any> = mutableMapOf("ruta" to Ruta.MI_PERFIL, "tipo" to TipoNotificacion.RECHAZAR_RESENIA)

        val notificacion = Notificacion(usuarioCreador.tokenNotificaciones, "${usuario.nombre} ha rechazado tu reseña").apply {
            data = dataNotificacion
        }
        notificacionService.enviarNotificacion(notificacion)

        return resenia.get()
    }
}
