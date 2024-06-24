package com.gamerly.projectgamerly.dtos

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import java.time.LocalDate
import java.time.Period

class UsuarioDetalleDTO() {
    var id : Long = 0
    lateinit var nombre: String
    lateinit var foto: String
    var edad: Int = 0
    lateinit var email: String
    lateinit var password: String
    lateinit var juegosPreferidos: List<String>
//    lateinit var diasHorariosPreferidos: Set<String>
    lateinit var plataformas: Set<String>
    lateinit var nacionalidad: String
    var reputacion: Long = 0
    var amigos: List<AgregarAmigoDTO> = mutableListOf()
    lateinit var resenias: ReseniasDTO

    constructor(
        usuario: Usuario,
        resenia: ReseniasDTO
    ) : this() {
        this.id = usuario.id
        this.nombre = usuario.nombre
        this.edad = Period.between(usuario.fechaDeNacimiento, LocalDate.now()).years
        this.foto = usuario.foto
        this.email = usuario.email
        this.password = usuario.password
        this.juegosPreferidos = usuario.juegosPreferidos.map { it.nombre }
//        this.diasHorariosPreferidos = usuario.diasHorariosPreferidos
        this.plataformas = usuario.plataformas.map { it.nombre }.toSet()
        this.nacionalidad = usuario.nacionalidad
        this.amigos = emptyList()
        //this.amigos = usuario.amigos.map { amigo ->
        //    AgregarAmigoDTO(idUsuario = this.id, idAmigo = amigo.id)
        //}
        this.reputacion = usuario.resenias.map { it.puntaje }.average().toLong()
        this.resenias = resenia
    }
}