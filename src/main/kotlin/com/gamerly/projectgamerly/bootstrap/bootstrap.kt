package com.gamerly.projectgamerly.bootstrap

import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.repos.ReviewRepository
import com.gamerly.projectgamerly.repos.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class Bootstrap: InitializingBean {
    @Autowired
    lateinit var usuarioRepository : UserRepository
    @Autowired
    lateinit var reseniaRepository : ReviewRepository
    lateinit var usuario1 :Usuario
    lateinit var usuario2 : Usuario
    lateinit var usuario3 : Usuario
    lateinit var resenia1 : Resenia
    lateinit var resenia2: Resenia
    lateinit var resenia3: Resenia

    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running bootstrap")
        println("************************************************************************")
        init()
    }

    fun init() {
        this.usuario()
        this.resenias()
        this.obtenerResenias()
    }

    fun usuario() {
        usuario1 = Usuario(
            "Nanami",
            "https://imagen.nextn.es/wp-content/uploads/2018/06/1807-03-Pok%C3%A9mon-GO-Squirtle-gafas-de-sol.jpg?strip=all&lossy=1&ssl=1",
            LocalDate.of(1999, 1, 1),
            "test@gmail.com",
            "usuarioFafa",
            listOf("lol", "terraria"),
            setOf("viernes", "sabado"),
            "Argentina",
            setOf("PC", "PS4")
            )
        usuario2 = Usuario(
            "Michael",
            "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/152.png",
            LocalDate.of(1999, 12, 1),
            "test2@gmail.com",
            "contrasenia",
            listOf("stardew valley", "overcooked"),
            setOf("viernes", "sabado"),
            "Argentina",
            setOf("PC")
        )
        usuario3 = Usuario(
            "Nicolas",
            "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/054.png",
            LocalDate.of(1999, 9, 1),
            "test1@gmail.com",
            "contrasenia",
            listOf("Fifa24", "WWE 2k23, ternaria","ETS2"),
            setOf("miercoles", "martes"),
            "Argentina",
            setOf("PC", "PS4", "XBOX")
            )
        usuarioRepository.saveAll(listOf(usuario1, usuario2, usuario3 ))
    }



    fun resenias() {
        val userEmisor1=usuarioRepository.findById(usuario1.id)
        val user2Receptor=usuarioRepository.findById(usuario2.id)
        val usuarioEmisor3=usuarioRepository.findById(usuario3.id)

        resenia1 = Resenia(
            userEmisor1.get().id,
            user2Receptor.get().id,
            4,
            "Buen compañero"
        )
        resenia2 = Resenia(
            user2Receptor.get().id,
            userEmisor1.get().id,
            1,
            "mereces perma por manco"
        )

        resenia3= Resenia(
            usuarioEmisor3.get().id,
            user2Receptor.get().id,
            5,
            "Amigo pasame la receta para ganar en el fifa"
        )

        reseniaRepository.saveAll(listOf(resenia1, resenia2))
    }

    fun obtenerResenias() {
        usuario1.also {
            it.resenias = mutableListOf(resenia1)
            usuarioRepository.save(it)
        }
        usuario2.also {
            it.resenias = mutableListOf(resenia2, resenia3)
            usuarioRepository.save(it)
        }
    }
}
