/* Estan pegando cosas en memoria y la idea es usar el repositorio para pegar cosas en la base de datos usando las consultas de jpa que incluye
para los filtros pueden pegar con consultas de sql
 */

//package com.gamerly.projectgamerly.repos
//
//import com.gamerly.projectgamerly.domain.Usuario
//import org.springframework.stereotype.Repository
//import java.time.LocalDate
//
//@Repository
//class UsuarioRepository {
//    val usuarios = mutableListOf<Usuario>();
//
//    fun getUsuarioByID(id: Long): Usuario {
//        return usuarios[id.toInt()]
//    }
//
////    fun agregarUsuario(nombre: String, fechaDeNacimiento: LocalDate, email: String, password: String) {
////        usuarios.add(
////            Usuario(
////                usuarios.count()+1.toLong(),
////                nombre,
////                "foto default",
////                fechaDeNacimiento,
////                email,
////                password,
////                listOf(),
////                listOf(),
////
////            )
////        )
////    }
//
//    fun matchUsuario(usuario: Usuario, juegosFilter: List<String>?, puntuacion: Long?): Boolean{
//        var result = true
//
//        if(juegosFilter != null){
//            if(!usuario.juegosPreferidos.any{juego -> juegosFilter.contains(juego)}){
//                result = false
//            }
//        }
//
//        if(puntuacion != null){
//            if(usuario.reputacion < puntuacion){
//                result = false
//            }
//        }
//
//        return result
//    }
//
//    fun busquedaAvanzada(juegosEnComun: List<String>?, puntuacion: Long?): List<Usuario>{
//        return usuarios.filter{usuario -> matchUsuario(usuario, juegosEnComun, puntuacion) }
//    }
//}