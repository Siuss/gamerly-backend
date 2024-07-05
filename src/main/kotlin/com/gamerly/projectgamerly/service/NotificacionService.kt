package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.Notificacion
import com.gamerly.projectgamerly.domain.Usuario
import io.github.jav.exposerversdk.ExpoPushMessage
import io.github.jav.exposerversdk.ExpoPushTicket
import io.github.jav.exposerversdk.PushClient
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class NotificacionService {
    fun enviarNotificacion(notificacion: Notificacion){

        // Si el token no es valido se tira un error
        if (!PushClient.isExponentPushToken(notificacion.tokenReceptor)) {
            throw Error("El token de notificacion: ${notificacion.tokenReceptor} no es valido.")
        }

        // Se crea la notificacion en la libreria del server de expo
        // usando la notificacion del dominio
        val notificacionExpo = ExpoPushMessage()
        notificacionExpo.addTo(notificacion.tokenReceptor)
        notificacionExpo.title = notificacion.titulo
        notificacionExpo.subtitle = notificacion.titulo

        if(notificacion.data !== null){
            notificacionExpo.data = notificacion.data
        }
        
        if(notificacion.mensaje != null) {
            notificacionExpo.body = notificacion.mensaje
        }

        // Se mete la notificacion en una lista porque la
        // libreria de expo lo necesita asi
        val listaNotificacionesExpo = listOf(notificacionExpo)

        // Se crea el objeto del expo server q va a mandar la notificacion
        val client = PushClient()

        // Se obtiene un expo ticket, que no estoy seguro que es, pero supongamos
        // que es como un "ticket de tren" que va a usar la notificacion para enviarse
        val expoTicket = client.sendPushNotificationsAsync(listaNotificacionesExpo).get()

        // Se despacha la notificacion con su "ticket de tren" que le da permiso para viajar
        client.zipMessagesTickets(listaNotificacionesExpo, expoTicket)

        // Adoptamos postura "best effort", es decir, si la notificacion llega bien, si hay error por ahora no lo manejamos
    }
}