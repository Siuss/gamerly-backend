package com.gamerly.projectgamerly.domain

import java.time.LocalDate

class Usuario (
    var nombre: String,
    var foto: String,
    var fechaDeNacimiento : LocalDate,
    var email: String,
    var password: String,
    var juegosPreferidos: List<String>,
    var diasPreferidos: List<String>,
    var id : Int
) {

}

/*
 nombre
 foto
 edad
 sexo
 juegos preferidos
 dias preferido
 horarios preferido
 */