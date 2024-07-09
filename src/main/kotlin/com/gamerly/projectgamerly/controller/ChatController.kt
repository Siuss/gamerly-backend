package com.gamerly.projectgamerly.controller

import com.gamerly.projectgamerly.domain.Chat
import com.gamerly.projectgamerly.domain.Juego
import com.gamerly.projectgamerly.dtos.ChatDTO
import com.gamerly.projectgamerly.dtos.ComunidadDTO
import com.gamerly.projectgamerly.dtos.MensajeDTO
import com.gamerly.projectgamerly.dtos.NuevoMensajeDTO
import com.gamerly.projectgamerly.service.ChatService
import com.gamerly.projectgamerly.service.JuegoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
class ChatController {
    @Autowired
    lateinit var chatService: ChatService

    @PostMapping("{idCreador}/nuevo-chat/{idReceptor}")
    fun nuevoChat(@PathVariable idCreador: Long, @PathVariable idReceptor: Long): ChatDTO {
        return ChatDTO.from(chatService.nuevoChat(idCreador, idReceptor))
    }

    @GetMapping("/chats/{idUsuario}")
    fun getChatsDelUsuario(@PathVariable idUsuario: Long): List<ChatDTO> {
        return chatService.getChatsDelUsuario(idUsuario).map{ChatDTO.from(it)}
    }

    @PostMapping("/mensaje/{idChat}")
    fun nuevoChat(@PathVariable idChat: Long, @RequestBody mensaje: NuevoMensajeDTO): MensajeDTO {
        return MensajeDTO.from(chatService.nuevoMensaje(idChat, mensaje))
    }
}