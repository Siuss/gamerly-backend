package com.gamerly.projectgamerly

import com.gamerly.projectgamerly.domain.Usuario

/**
 * TODO: Corresponde incluir en Usuario los atributos faltantes
 */
class Búsqueda(private val repositorio: List<Usuario>) {

    fun buscarUsuariosDe(preferido: Usuario): List<Usuario> {
        val usuarioCoincidentes = mutableListOf<Usuario>()

        for (usuario in repositorio) {
            if (coincideConCriterios(usuario)) {
                usuarioCoincidentes.add(usuario, preferido)
            }
        }

        return usuarioCoincidentes
    }

    private fun coincideConCriterios(usuario: Usuario, preferido: Usuario): Boolean {
        
        if ((usuario.juegosPreferidos == preferido.juegosPreferidos) ||
            (usuario.reputacion == preferido.reputacion) ||
            (usuario.diasPreferidos == preferido.diasPreferidos) ||
            (usuario.horariosPreferidos == preferido.horariosPreferidos)){
            return true
        }
        
    }
}