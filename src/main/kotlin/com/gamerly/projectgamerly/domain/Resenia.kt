package com.gamerly.projectgamerly.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import java.time.LocalDate
import java.time.LocalTime

/*
*
* aca podriamos agregar la fecha y hora como columna x ahora, te sale la importacion de la columna Juili*/
//puede ser siii
@Entity
class Resenia(
    @JoinColumn(name = "usuario_id")
    var idUsuarioEmisor: Long,
    @Column
    var puntaje: Int,
    @Column
    var comentario: String,
    @Column
    var fecha : LocalDate = LocalDate.now(),
    @Column
    var hora : LocalTime = LocalTime.now()
    ) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0


}