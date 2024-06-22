package com.gamerly.projectgamerly.domain

import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import jakarta.persistence.*

@Entity
//@Table(name = "diasHorarios")
data class DíasHorariosPreferidos(
    @Enumerated(EnumType.STRING)
    val diaDeLaSemana: DiaDeLaSemana,

    @Enumerated(EnumType.STRING)
    val horarioFavorito: HorariosFavoritos,

    @ManyToOne
    val usuario: Usuario // Relación con la entidad Usuario
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
