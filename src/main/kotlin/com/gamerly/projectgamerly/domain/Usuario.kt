package com.gamerly.projectgamerly.domain

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.LocalDate

//class Usuario(
//	var id: Long,
//	var nombre: String,
//	var foto: String,
//	var fechaDeNacimiento: LocalDate,
//	var email: String,
//	var password: String,
//	var juegosPreferidos: List<String>,
//	var diasPreferidos: List<String>,
//	var reputacion: Long,
//	var resenias: List<Resenia>
//) {
//
//}
//
//



@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var nombre: String,

    @Column(nullable = false)
    var foto: String,

    @Column(name = "fecha_de_nacimiento", nullable = false)
    var fechaDeNacimiento: LocalDate,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @ElementCollection
    @CollectionTable(name = "usuario_juegos_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "juego_preferido")
    var juegosPreferidos: List<String> = mutableListOf(),

    @ElementCollection
    @CollectionTable(name = "usuario_dias_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "dia_preferido")
    var diasPreferidos: List<String> = mutableListOf()
)

