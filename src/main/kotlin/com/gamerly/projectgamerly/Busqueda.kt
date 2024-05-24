package com.gamerly.projectgamerly

import com.gamerly.projectgamerly.domain.Usuario

/**
 * Se reemplazó el bucle for (if anidado) con función filter.
 */
class Búsqueda(private val repositorio: List<Usuario>) {

    fun buscarUsuariosDe(preferido: Usuario): List<Usuario> {
        return repositorio.filter { coincideConCriterios(it, preferido) }
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