package com.gamerly.projectgamerly.repos

import com.gamerly.projectgamerly.domain.Usuario
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<Usuario, Long>{

}