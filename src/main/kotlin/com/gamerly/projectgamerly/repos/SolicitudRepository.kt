package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Solicitud
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SolicitudRepository: JpaRepository<Solicitud, Long> {
    fun findByUsuarioCreador_IdAndUsuarioReceptor_Id(idCreador: Long, idAmigo: Long): Optional<Solicitud>

    fun findByUsuarioReceptor_Id(idUsuario: Long): List<Solicitud>
}