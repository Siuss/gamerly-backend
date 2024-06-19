package com.gamerly.projectgamerly.domain

import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
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

    @ElementCollection(targetClass = DiaDeLaSemana::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_dia_favorito", joinColumns = [JoinColumn(name = "usuario_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_favorito")
    var diaFavorito: List<DiaDeLaSemana> = mutableListOf(),

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @ElementCollection
    @CollectionTable(name = "usuario_juegos_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "juego_preferido")
    var juegosPreferidos: MutableList<String> = mutableListOf(),

    @ElementCollection(targetClass = HorariosFavoritos::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_dias_horarios_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "dia_horario_preferido")
    var horariosPreferidos: List<HorariosFavoritos> = mutableListOf(),
    //para construir el docker primero necesitas en la terminal escribir docker compose up -d para levantarlo
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



