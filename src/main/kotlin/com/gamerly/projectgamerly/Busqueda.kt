package com.gamerly.projectgamerly

import com.gamerly.projectgamerly.domain.Usuario

/**
 * Se reemplazó el bucle for (if anidado) con función filter.
 */
class Búsqueda(private val repositorio: List<Usuario>) {

    fun buscarUsuariosDe(preferencia: Usuario): List<Usuario> {
        return repositorio.filter { coincideConCriterios(it, preferencia) }
    }

    private fun coincideConCriterios(usuario: Usuario, preferido: Usuario): Boolean {
        
        if ((usuario.juegosPreferidos == preferido.juegosPreferidos) ||
            (usuario.reputacion == preferido.reputacion) ||
            (usuario.diasPreferidos == preferido.diasPreferidos) ||
            (usuario.horariosPreferidos == preferido.horariosPreferidos)){
            return true
        }
        return false
    }

    fun buscarUsuarios(
        juegosPreferidos: List<String>?,
        reputacion: Int?,
        diasPreferidos: List<String>?,
        horariosPreferidos: List<String>?
    ): List<Usuario> {
        return repositorio.filter { usuario ->
            (juegosPreferidos == null || usuario.juegosPreferidos.intersect(juegosPreferidos).isNotEmpty()) &&
            (reputacion == null || usuario.reputacion == reputacion) && 
            (diasPreferidos == null || usuario.diasPreferidos.intersect(diasPreferidos).isNotEmpty()) &&
            (horariosPreferidos == null || usuario.diasPreferidos.intersect(horariosPreferidos).isNotEmpty())) 
        }
    }
}