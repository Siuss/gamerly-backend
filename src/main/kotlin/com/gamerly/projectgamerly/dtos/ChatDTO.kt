package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Chat

class ChatDTO {
    var id : Long = 0
    lateinit var mensajes: List<MensajeDTO>
    lateinit var usuario1: UsuarioMensajeDTO
    lateinit var usuario2: UsuarioMensajeDTO

    companion object {
        fun from(chat: Chat): ChatDTO = ChatDTO().also { dto ->
            dto.id = chat.id
            dto.mensajes = chat.mensajes.map{MensajeDTO.from(it)}
            dto.usuario1 = UsuarioMensajeDTO.from(chat.usuario1)
            dto.usuario2 = UsuarioMensajeDTO.from(chat.usuario2)
        }
    }
}