package com.gamerly.projectgamerly.bootstrap

import com.gamerly.projectgamerly.domain.*
import com.gamerly.projectgamerly.repos.GameRepository
import com.gamerly.projectgamerly.repos.ReviewRepository
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

@Service
class Bootstrap: InitializingBean {
    @Autowired
    lateinit var usuarioRepository : UserRepository
    @Autowired
    lateinit var reseniaRepository : ReviewRepository
    @Autowired
    lateinit var juegoRepository : GameRepository
    lateinit var usuario1: Usuario
    lateinit var usuario2: Usuario
    lateinit var usuario3: Usuario
    lateinit var resenia1: Resenia
    lateinit var resenia2: Resenia
    lateinit var resenia3: Resenia
    var listaJuegos = mutableListOf<Juego>()

    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running bootstrap")
        println("************************************************************************")
        init()
    }

    fun init() {
        this.juegos()
        this.usuario()
        this.resenias()
        this.obtenerResenias()
    }

    fun juegos() {
        listaJuegos.addAll(
            listOf(
                Juego(
                    "FIFA 23",
                    "https://images.igdb.com/igdb/image/upload/t_original/sci00g.webp",
                    listOf(
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.PLAYSTATION5,
                        Plataformas.XBOXSERIESX
                    )
                ),
                Juego(
                    "Counter-Strike: Global Offensive",
                    "https://images.igdb.com/igdb/image/upload/t_original/ark8i.webp",
                    listOf(
                        Plataformas.LINUX,
                        Plataformas.MAC,
                        Plataformas.WINDOWS
                    )
                ),
                Juego(
                    "League of Legends",
                    "https://images.igdb.com/igdb/image/upload/t_original/sclnve.webp",
                    listOf(
                        Plataformas.MAC,
                        Plataformas.WINDOWS
                    )
                ),
                Juego(
                    "Euro Truck Simulator 2",
                    "https://images.igdb.com/igdb/image/upload/t_original/scgqh4.webp",
                    listOf(
                        Plataformas.LINUX,
                        Plataformas.MAC,
                        Plataformas.WINDOWS
                    )
                ),
                Juego(
                    "Pummel Party",
                    "https://images.igdb.com/igdb/image/upload/t_original/arqdt.webp",
                    listOf(
                        Plataformas.NINTENDOSWITCH,
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.XBOXONE
                    )
                ),
                Juego(
                    "Rocket League",
                    "https://images.igdb.com/igdb/image/upload/t_original/k1hcu9flbu0tvheine15.webp",
                    listOf(
                        Plataformas.LINUX,
                        Plataformas.MAC,
                        Plataformas.NINTENDOSWITCH,
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.PLAYSTATION5,
                        Plataformas.XBOXONE,
                        Plataformas.XBOXSERIESX
                    )
                ),
                Juego(
                    "Lethal Company",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar1rug.webp",
                    listOf(
                        Plataformas.WINDOWS
                    )
                ),
                Juego(
                    "Content Warning",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar2v8v.webp",
                    listOf(
                        Plataformas.WINDOWS
                    )
                ),
                Juego(
                    "Terraria",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar2voa.webp",
                    listOf(
                        Plataformas.ANDROID,
                        Plataformas.IOS,
                        Plataformas.LINUX,
                        Plataformas.MAC,
                        Plataformas.NINTENDOSWITCH,
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.XBOXONE,
                        Plataformas.XBOXSERIESX
                    )
                ),
                Juego(
                    "Overcooked!",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar6xz.webp",
                    listOf(
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.XBOXONE
                    )
                ),
                Juego(
                    "Astroneer",
                    "https://images.igdb.com/igdb/image/upload/t_original/sci95s.webp",
                    listOf(
                        Plataformas.NINTENDOSWITCH,
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.XBOXONE
                    )
                ),
                Juego(
                    "Left 4 Dead 2",
                    "https://images.igdb.com/igdb/image/upload/t_original/arq5o.webp",
                    listOf(
                        Plataformas.LINUX,
                        Plataformas.MAC,
                        Plataformas.WINDOWS
                    )
                ),
                Juego(
                    "Project Zomboid",
                    "https://images.igdb.com/igdb/image/upload/t_original/ihmtywjhagxtdzzf1wrw.webp",
                    listOf(
                        Plataformas.LINUX,
                        Plataformas.MAC,
                        Plataformas.WINDOWS
                    )
                ),
                Juego(
                    "Stardew Valley",
                    "https://images.igdb.com/igdb/image/upload/t_original/sw7rtba7p1xs77klsime.webp",
                    listOf(
                        Plataformas.ANDROID,
                        Plataformas.IOS,
                        Plataformas.LINUX,
                        Plataformas.MAC,
                        Plataformas.NINTENDOSWITCH,
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.XBOXONE
                    )
                ),
                Juego(
                    "Deep Rock Galactic",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar4nr.webp",
                    listOf(
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.PLAYSTATION5,
                        Plataformas.XBOXONE,
                        Plataformas.XBOXSERIESX
                    )
                ),
                Juego(
                    "Among Us",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar7tq.webp",
                    listOf(
                        Plataformas.ANDROID,
                        Plataformas.IOS,
                        Plataformas.NINTENDOSWITCH,
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.PLAYSTATION5,
                        Plataformas.XBOXONE,
                        Plataformas.XBOXSERIESX
                    )
                ),
                Juego(
                    "Phasmophobia",
                    "https://images.igdb.com/igdb/image/upload/t_original/ardfr.webp",
                    listOf(
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION5,
                        Plataformas.XBOXSERIESX)
                ),
                Juego(
                    "Dead by Daylight",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc6vlh.webp",
                    listOf(
                        Plataformas.NINTENDOSWITCH,
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.PLAYSTATION5,
                        Plataformas.XBOXONE,
                        Plataformas.XBOXSERIESX
                    )
                ),
                Juego(
                    "Sonic & All-Stars Racing Transformed",
                    "https://images.igdb.com/igdb/image/upload/t_original/j679caufkftsns3n26df.webp",
                    listOf(
                        Plataformas.ANDROID,
                        Plataformas.IOS,
                        Plataformas.WINDOWS
                    )
                ),
                Juego(
                    "WWE 2K23",
                    "https://images.igdb.com/igdb/image/upload/t_original/sckovg.webp",
                    listOf(
                        Plataformas.WINDOWS,
                        Plataformas.PLAYSTATION4,
                        Plataformas.PLAYSTATION5,
                        Plataformas.XBOXONE,
                        Plataformas.XBOXSERIESX
                    )
                )
            )
        )
        juegoRepository.saveAll(listaJuegos)
    }

    fun usuario() {
        usuario1 = Usuario(
            "Nanami",
            "https://imagen.nextn.es/wp-content/uploads/2018/06/1807-03-Pok%C3%A9mon-GO-Squirtle-gafas-de-sol.jpg?strip=all&lossy=1&ssl=1",
            LocalDate.of(1999, 1, 1),
            mutableSetOf(DiaDeLaSemana.SABADO, DiaDeLaSemana.DOMINGO),
            "test@gmail.com",
            "usuarioFafa",
            mutableSetOf(
                juegoRepository.findJuegoByNombre("League of Legends"),
                juegoRepository.findJuegoByNombre("Terraria")
            ),
            mutableListOf(HorariosFavoritos.NOCHE, HorariosFavoritos.TARDE),
            "Argentina",
            setOf(
                Plataformas.WINDOWS,
                Plataformas.PLAYSTATION4
            )
        )
        usuario2 = Usuario(
            "Usuario 2",
            "https://descubre.rci.com/wp-content/uploads/2019/08/800x500px_Bariloche2_A.png",
            LocalDate.of(1999, 1, 1),
            mutableSetOf(DiaDeLaSemana.SABADO, DiaDeLaSemana.DOMINGO),
            "test2@gmail.com",
            "123",
            mutableSetOf(
                juegoRepository.findJuegoByNombre("Stardew Valley"),
                juegoRepository.findJuegoByNombre("Content Warning"),
                juegoRepository.findJuegoByNombre("Overcooked!")
            ),
            mutableListOf(HorariosFavoritos.NOCHE, HorariosFavoritos.TARDE),
            "Argentina",
            setOf(
                Plataformas.WINDOWS
            )
        )
        usuario3 = Usuario(
            "Nicolas",
            "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/054.png",
            LocalDate.of(1999, 9, 1),
            mutableSetOf(DiaDeLaSemana.VIERNES,DiaDeLaSemana.SABADO, DiaDeLaSemana.DOMINGO),
            "test1@gmail.com",
            "contrasenia",
            mutableSetOf(
                juegoRepository.findJuegoByNombre("FIFA 23"),
                juegoRepository.findJuegoByNombre("WWE 2K23"),
                juegoRepository.findJuegoByNombre("Terraria")
            ),
            mutableListOf(HorariosFavoritos.MAÑANA, HorariosFavoritos.NOCHE),
            "Argentina",
            setOf(
                Plataformas.NINTENDOSWITCH,
                Plataformas.WINDOWS,
                Plataformas.PLAYSTATION4,
                Plataformas.XBOXONE
            )
        )
        usuarioRepository.saveAll(listOf(usuario1, usuario2, usuario3))
    }



    fun resenias() {
        val user1= usuarioRepository.findById(usuario1.id).get()
        val user2= usuarioRepository.findById(usuario2.id).get()
        val user3= usuarioRepository.findById(usuario3.id).get()

        resenia1 = Resenia(
            usuario2.id,
            4,
            "Buen compañero",
            LocalDate.of(2024, 2, 1),
            LocalTime.of(12,30)
        )
        user1.addResenia(resenia1)

        resenia2 = Resenia(
            usuario3.id,
            1,
            "mereces perma por malardo",
            LocalDate.of(2024, 4, 1),
            LocalTime.of(18,30)
        )
        user2.addResenia(resenia2)

        resenia3= Resenia(
            usuario1.id,
            5,
            "Amigo pasame la receta para ganar en el fifa",
            LocalDate.of(2024, 2, 21),
            LocalTime.of(22,30)
        )
        user3.addResenia(resenia3)

        reseniaRepository.saveAll(listOf(resenia1, resenia2, resenia3))
        usuarioRepository.saveAll(listOf(user1, user2, user3))
    }

    fun obtenerResenias() {
        usuario1.also {
            it.resenias = mutableSetOf(resenia1)
            usuarioRepository.save(it)
        }
        usuario2.also {
            it.resenias = mutableSetOf(resenia2)
            usuarioRepository.save(it)
        }
        usuario3.also {
            it.resenias = mutableSetOf(resenia3)
            usuarioRepository.save(it)
        }
    }
}
