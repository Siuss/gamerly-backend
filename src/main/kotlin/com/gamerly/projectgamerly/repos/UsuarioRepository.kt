package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Usuario
import org.springframework.stereotype.Repository

@Repository
class UsuarioRepository {
    val usuarios = mutableListOf<Usuario>();

    fun matchUsuario(usuario: Usuario, juegosFilter: List<String>?, puntuacion: Long?): Boolean{
        var result = true

        if(juegosFilter != null){
            if(!usuario.juegosPreferidos.any{juego -> juegosFilter.contains(juego)}){
                result = false
            }
        }

        if(puntuacion != null){
            if(usuario.reputacion < puntuacion){
                result = false
            }
        }

        return result
    }

    fun busquedaAvanzada(juegosEnComun: List<String>?, puntuacion: Long?): List<Usuario>{
        return usuarios.filter{usuario -> matchUsuario(usuario, juegosEnComun, puntuacion) }
    }
}