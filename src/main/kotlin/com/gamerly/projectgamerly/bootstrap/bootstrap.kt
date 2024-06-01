package com.gamerly.projectgamerly.bootstrap

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.repos.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class Bootstrap: InitializingBean {
    @Autowired
    lateinit var usuarioRepository : UserRepository

    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running bootstrap")
        println("************************************************************************")
        init()
    }

    fun init() {
//        val resenia12 = Resenia(2, 1, 4, "Buen compañero")
//        val resenia21 = Resenia(1, 2, 3, "Ni bien ni mal")

        val usuario1 = Usuario(
            "Nanami",
            "https://imagen.nextn.es/wp-content/uploads/2018/06/1807-03-Pok%C3%A9mon-GO-Squirtle-gafas-de-sol.jpg?strip=all&lossy=1&ssl=1",
            LocalDate.of(1999, 1, 1),
            "test@gmail.com",
            "usuarioFafa",
            listOf("lol", "terraria"),
            listOf("viernes", "sabado"),
            "Argentina"


        )
//        val usuario2 = Usuario(
//            2,
//            "Usuario 2",
//            "https://descubre.rci.com/wp-content/uploads/2019/08/800x500px_Bariloche2_A.png",
//            LocalDate.now(),
//            "test2@gmail.com",
//            "123",
//            listOf("stardew valley", "overcooked"),
//            listOf("miercoles", "martes"),
//            3,
//            listOf(resenia21)
//        )

        usuarioRepository.saveAll(listOf(usuario1))
    }
}
