package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Mensaje
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun formatFecha(fecha: LocalDateTime): String{
    return DateTimeFormatter.ofPattern("E d '**' MMMM HH:mm", Locale.of("es","ES")).format(fecha).split(' ')
        .joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }.replace("**", "de")
}

fun formatFecha(fecha: LocalDate): String{
    return DateTimeFormatter.ofPattern("dd/MM/YYYY", Locale.of("es","ES")).format(fecha)
}

class MensajeDTO (
    var id: Long,
    var idCreador: Long,
    var idReceptor: Long,
    var contenido: String,
    var fecha: String,
    var leido: Boolean
){
    companion object {
        fun from(mensaje: Mensaje): MensajeDTO = MensajeDTO(
            id = mensaje.id,
            idCreador = mensaje.usuarioCreador.id,
            idReceptor = mensaje.usuarioReceptor.id,
            contenido = mensaje.contenido,
            fecha = formatFecha(mensaje.fecha),
            leido = mensaje.leido
        )
    }

}

