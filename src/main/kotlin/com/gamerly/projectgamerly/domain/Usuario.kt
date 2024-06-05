package com.gamerly.projectgamerly.domain

import jakarta.persistence.*
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
class Usuario(
    @Column(nullable = false)
    var nombre: String = "",

    @Column(nullable = false)
    var foto: String = "",

    @Column(name = "fecha_de_nacimiento", nullable = false)
    var fechaDeNacimiento: LocalDate = LocalDate.now(),

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @ElementCollection
    @CollectionTable(name = "usuario_juegos_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "juego_preferido")
    var juegosPreferidos: List<String> = mutableListOf(),

    @ElementCollection
    @CollectionTable(name = "usuario_dias_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "dia_preferido")
    var diasPreferidos: Set<String> = mutableSetOf(),

    @Column(nullable = false)
    var nacionalidad: String = "",

    @ElementCollection
    @CollectionTable(name = "usuario_plataformas", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "plataforma")
    var plataformas: Set<String> = mutableSetOf(),


    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var resenias: List<Resenia> = mutableListOf()

}



