package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Reporte
import com.gamerly.projectgamerly.dtos.NuevoReporteDTO
import com.gamerly.projectgamerly.repos.ReporteRepository
import com.gamerly.projectgamerly.utilities.ReporteYaExiste
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReporteService {
    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var reporteRepository: ReporteRepository

    @Autowired
    private lateinit var emailService: EmailService

    @Autowired
    private lateinit var chatService: ChatService

    fun nuevoReporte(idCreador: Long, idReportado: Long, reporte: NuevoReporteDTO): Long {
        if(idCreador == idReportado){
            throw ReporteYaExiste("No se puede reportar a uno mismo")
        }

        val usuarioCreador = usuarioService.getUsuario(idCreador)
        val usuarioReportado = usuarioService.getUsuario(idReportado)

        val nuevoReporte = Reporte(usuarioCreador, usuarioReportado, reporte.contenido)

        reporteRepository.save(nuevoReporte)

        // Si ambos usuarios tienen un chat se obtienen los ultimos 20 mensajes
        val cantidadDeMensajes = 20
        var ultimoChatString = ""
        val ultimoChat = chatService.getChat(idCreador, idReportado)

        if(ultimoChat != null){
            ultimoChatString="\r\n\r\nEstos son los ultimos ${cantidadDeMensajes} mensajes del chat (id: ${ultimoChat.id}) entre ambos:\r\n"

            ultimoChat.mensajes.takeLast(cantidadDeMensajes).forEach{mensaje ->
                run {
                    ultimoChatString += "${mensaje.usuarioCreador.nombre}: ${mensaje.contenido}\r\n"
                }
            }
        }

        // Se envia un reporte por mail al equipo de gamerly
        emailService.enviarMail("spazosrubio@estudiantes.unsam.edu.ar",
            "Gamerly - Se ha recibido un nuevo reporte",
            "El usuario ${usuarioCreador.nombre} (email: ${usuarioCreador.email}, id: ${usuarioCreador.id}) emitio un reporte sobre el usuario ${usuarioReportado.nombre} (email: ${usuarioCreador.email}, id: ${usuarioReportado.id}) con el siguiente mensaje:\r\n\r\n ${reporte.contenido}$ultimoChatString")

        return getCantidadReportesDelUsuario(usuarioReportado.id)
    }

    fun getCantidadReportesDelUsuario(idUsuario: Long): Long{
       return reporteRepository.countDistinctByUsuarioReportadoId(idUsuario)
    }
}
