package com.gamerly.projectgamerly.bootstrap

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.repos.UsuarioRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class Bootstrap: InitializingBean {
    @Autowired
    val usuarioRepository = UsuarioRepository()

    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running bootstrap")
        println("************************************************************************")
        init()
    }

    fun init() {
        val resenia12 = Resenia(2, 1, 4, "Buen compañero")
        val resenia21 = Resenia(1, 2, 3, "Ni bien ni mal")

        val usuario1 = Usuario(
            1,
            "Nanami",
            "https://descubre.rci.com/wp-content/uploads/2019/08/800x500px_Bariloche2_A.png",
            LocalDate.now(),
            "test@gmail.com",
            "123",
            listOf("lol", "terraria"),
            listOf("viernes", "sabado"),

        )
        val usuario2 = Usuario(
            2,
            "Usuario 2",
            "https://descubre.rci.com/wp-content/uploads/2019/08/800x500px_Bariloche2_A.png",
            LocalDate.now(),
            "test2@gmail.com",
            "123",
            listOf("stardew valley", "overcooked"),
            listOf("miercoles", "martes"),
        )

        usuarioRepository.usuarios.addAll(listOf(usuario1, usuario2))
    }
}
