package com.gamerly.projectgamerly.service

import com.gamerly.projectgamerly.domain.*
import com.gamerly.projectgamerly.repos.ChatRepository
import com.gamerly.projectgamerly.repos.GameRepository
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.utilities.NoSePuedeBloquearASiMismo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BloqueosService {
    @Autowired
    lateinit var usuarioRepository: UserRepository
    @Autowired
    lateinit var chatRepository: ChatRepository
    @Autowired
    lateinit var usuarioService: UsuarioService
    @Autowired
    lateinit var chatService: ChatService


    @Transactional
    fun bloquearUsuario(idUsuario: Long, idBloqueado: Long): Usuario{
        if(idUsuario == idBloqueado) {
            throw NoSePuedeBloquearASiMismo("Un usuario no puede bloquearse a si mismo")
        }

        val usuario = usuarioService.getUsuario(idUsuario)
        val bloqueado = usuarioService.getUsuario(idBloqueado)

        bloqueado.amigos = bloqueado.amigos.filter{ it.id != idUsuario }.toMutableSet()
        usuario.amigos = usuario.amigos.filter{ it.id != idBloqueado }.toMutableSet()
        usuario.bloqueados.add(bloqueado)
        usuario.reseniasPendientes = usuario.reseniasPendientes.filter{it.idUsuarioEmisor != idBloqueado}.toMutableSet()

        val chat = chatService.getChat(idUsuario, idBloqueado)
        if(chat != null){
            chatRepository.delete(chat)
        }

        usuarioRepository.save(usuario)
        val usuarioGuardado = usuarioRepository.save(bloqueado)
        return usuarioGuardado
    }

    fun desbloquearUsuario(idUsuario: Long, idBloqueado: Long): Usuario{
        if(idUsuario == idBloqueado) {
            throw NoSePuedeBloquearASiMismo("Un usuario no puede desbloquearse a si mismo")
        }

        val usuario = usuarioService.getUsuario(idUsuario)
        val bloqueado = usuarioService.getUsuario(idBloqueado)

        usuario.bloqueados = usuario.bloqueados.filter { it.id != idBloqueado }.toMutableSet()

        usuarioRepository.save(usuario)
        val usuarioGuardado = usuarioRepository.save(bloqueado)
        return usuarioGuardado
    }

    fun getUsuarioEstaBloqueado(idUsuarioLogueado: Long, idUsuario: Long): Boolean{
        val usuario = usuarioService.getUsuario(idUsuario)

        return usuario.bloqueados.any{it.id == idUsuarioLogueado}
    }

    fun getUsuariosBloqueados(idUsuario: Long): MutableSet<Usuario>{
        val usuario = usuarioService.getUsuario(idUsuario)
        return usuario.bloqueados
    }
}
