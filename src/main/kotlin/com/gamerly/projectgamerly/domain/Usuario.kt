package com.gamerly.projectgamerly.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "usuarios")
class Usuario(
    @Column(nullable = false)
    var nombre: String = "",

    @Column(nullable = false)
    var foto: String = "",

    @Column(name = "fecha_de_nacimiento", nullable = false)
    var fechaDeNacimiento: LocalDate = LocalDate.now(),



    @Column(name = "dia_favorito")
    @Enumerated(EnumType.STRING)
    var diaFavorito: DiaDeLaSemana,


    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @ElementCollection
    @CollectionTable(name = "usuario_juegos_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "juego_preferido")
    var juegosPreferidos: List<String> = mutableListOf(),

    @ElementCollection
    @CollectionTable(name = "usuario_dias_horarios_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "dia_horario_preferido")
    var diasHorariosPreferidos: Set<String> = mutableSetOf(),

    //...buena idea toda la razon, estuve todo el dia asi jajajaja gracias stephy, mañana te molesto daleee, gracias nos vemooos igualmentee
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
    var resenias: MutableList<Resenia> = mutableListOf()

    fun addResenia(resenia: Resenia)  {
         resenias.add(resenia)
    }
}



