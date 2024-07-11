package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Reporte
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ReporteRepository: JpaRepository<Reporte, Long> {
    @Query("SELECT COALESCE((SELECT count(DISTINCT r.usuarioCreador.id) FROM Reporte r WHERE r.usuarioReportado.id = ?1 GROUP BY r.usuarioReportado.id), 0)")
    fun countDistinctByUsuarioReportadoId(idUsuario: Long): Long
}