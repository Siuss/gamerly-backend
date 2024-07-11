package com.gamerly.projectgamerly.service
import com.gamerly.projectgamerly.domain.Chat
import com.gamerly.projectgamerly.domain.Mensaje
import com.gamerly.projectgamerly.domain.Notificacion
import com.gamerly.projectgamerly.dtos.ChatDTO
import com.gamerly.projectgamerly.dtos.NuevoMensajeDTO
import com.gamerly.projectgamerly.repos.ChatRepository
import com.gamerly.projectgamerly.repos.MensajeRepository
import com.gamerly.projectgamerly.utilities.ChatNoExiste
import com.gamerly.projectgamerly.utilities.ChatYaExiste
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class ChatService {
    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var chatRepository: ChatRepository

    @Autowired
    private lateinit var mensajeRepository: MensajeRepository


    @Autowired
    private lateinit var notificacionService: NotificacionService

    fun getChat(idCreador: Long, idReceptor: Long): Chat?{
        val posibleChat1 = chatRepository.findByUsuario1_idAndUsuario2_id(idCreador, idReceptor)

        if(posibleChat1.isPresent){
            return posibleChat1.get()
        }

        val posibleChat2 = chatRepository.findByUsuario1_idAndUsuario2_id(idReceptor, idCreador)

        if(posibleChat2.isPresent){
            return posibleChat2.get()
        }

        return null
    }

    fun nuevoChat(idCreador: Long, idReceptor: Long): Chat {
        if(idCreador == idReceptor){
            throw ChatYaExiste("No se puede crear un chat con uno mismo")
        }

        val usuarioCreador = usuarioService.getUsuario(idCreador)
        val usuarioReceptor = usuarioService.getUsuario(idReceptor)

        val chatExistente = getChat(idCreador, idReceptor)

        if(chatExistente != null){
            throw ChatYaExiste("Ya existe un chat entre ambos usuarios")
        }

        val chat = Chat(0, usuarioCreador, usuarioReceptor, mutableListOf())
        chatRepository.save(chat)

        return chat
    }

    fun getChatsDelUsuario(idUsuario: Long): List<Chat>{
        val chats = chatRepository.findAllByUsuario1_idOrUsuario2_id(idUsuario, idUsuario)
        return chats
    }

    fun getChatById(idChat: Long): Optional<Chat> {
        val chat = chatRepository.findById(idChat)
        return chat
    }

    fun getChat(idChat: Long): Chat {
        val chat = chatRepository.findById(idChat)

        if(chat.isEmpty){
            throw ChatNoExiste("No existe un chat con ese id")
        }

        return chat.get()
    }


    @Transactional
    fun nuevoMensaje(idChat: Long, mensaje: NuevoMensajeDTO): Mensaje {
        val usuarioCreador = usuarioService.getUsuario(mensaje.idUsuarioCreador)
        val usuarioReceptor = usuarioService.getUsuario(mensaje.idUsuarioReceptor)

        val nuevoMensaje = Mensaje(0, usuarioCreador, usuarioReceptor, mensaje.contenido, LocalDateTime.now())

        val posibleChat = getChatById(idChat)

        if(posibleChat.isEmpty){
          throw ChatNoExiste("No existe un chat con ese id")
        }

        val chat = posibleChat.get()

        val mensajeCreado = mensajeRepository.save(nuevoMensaje)

        chat.mensajes.add(mensajeCreado)

        chatRepository.save(chat)

        // TODO: Agregar un data para redirigir derecho al chat
        val notificacion = Notificacion(usuarioReceptor.tokenNotificaciones, "${usuarioCreador.nombre} te ha enviado un mensaje", mensaje.contenido)
        notificacionService.enviarNotificacion(notificacion)

        return mensajeCreado
    }
}
