package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Resenia
import org.springframework.data.repository.CrudRepository

interface ReviewRepository : CrudRepository<Resenia, Long>{
}