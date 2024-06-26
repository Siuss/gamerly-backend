package com.gamerly.projectgamerly.domain

import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import jakarta.persistence.*

@Entity
@Table(name = "dia_horario")
data class DiaHorarioPreferido(

    @Enumerated(EnumType.STRING)
    val diaDeLaSemana: DiaDeLaSemana,

    @Enumerated(EnumType.STRING)
    val horarioFavorito: HorariosFavoritos,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
