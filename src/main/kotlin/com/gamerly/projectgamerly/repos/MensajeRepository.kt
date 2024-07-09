package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Mensaje
import org.springframework.data.jpa.repository.JpaRepository


interface MensajeRepository: JpaRepository<Mensaje, Long> {
}