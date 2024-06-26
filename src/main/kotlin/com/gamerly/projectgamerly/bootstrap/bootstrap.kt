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
    lateinit var usuario4: Usuario
    lateinit var usuario5: Usuario
    lateinit var usuario6: Usuario
    lateinit var resenia1: Resenia
    lateinit var resenia2: Resenia
    lateinit var resenia3: Resenia
    lateinit var resenia4: Resenia
    lateinit var resenia5: Resenia
    lateinit var resenia6: Resenia
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
                        Plataformas.PLAYSTATION5
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
                        Plataformas.PLAYSTATION4
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
                        Plataformas.XBOXSERIESX
                    )
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
            LocalDate.of(1999, 1, 5),
            "test1@gmail.com",
            "1234",
            mutableSetOf(
                juegoRepository.findJuegoByNombre("League of Legends"),
                juegoRepository.findJuegoByNombre("Terraria")
            ),
            mutableSetOf(),
            mutableSetOf(),
            mutableSetOf(),
            "Argentina",
            setOf(
                Plataformas.WINDOWS,
                Plataformas.PLAYSTATION4
            ),
            mutableSetOf(
                DiaHorarioPreferido(DiaDeLaSemana.SABADO, HorariosFavoritos.TARDE),
                DiaHorarioPreferido(DiaDeLaSemana.SABADO, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.DOMINGO, HorariosFavoritos.TARDE),
                DiaHorarioPreferido(DiaDeLaSemana.DOMINGO, HorariosFavoritos.NOCHE)
            ),
            "Nanami09"
        )

        usuario2 = Usuario(
            "NicolasMar",
            "https://descubre.rci.com/wp-content/uploads/2019/08/800x500px_Bariloche2_A.png",
            LocalDate.of(2001, 7, 1),
            "test2@gmail.com",
            "1234",
            mutableSetOf(
                juegoRepository.findJuegoByNombre("Stardew Valley"),
                juegoRepository.findJuegoByNombre("Content Warning"),
                juegoRepository.findJuegoByNombre("Overcooked!")
            ),
            mutableSetOf(),
            mutableSetOf(),
            mutableSetOf(),
            "Argentina",
            setOf(
                Plataformas.WINDOWS
            ),
            mutableSetOf(
                DiaHorarioPreferido(DiaDeLaSemana.VIERNES, HorariosFavoritos.TARDE),
                DiaHorarioPreferido(DiaDeLaSemana.VIERNES, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.SABADO, HorariosFavoritos.TARDE),
                DiaHorarioPreferido(DiaDeLaSemana.SABADO, HorariosFavoritos.NOCHE),
            ),
            "NicoMar"
        )

        usuario3 = Usuario(
            "Cirr",
            "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/054.png",
            LocalDate.of(1998, 9, 27),
            "test3@gmail.com",
            "1234",
            mutableSetOf(
                juegoRepository.findJuegoByNombre("FIFA 23"),
                juegoRepository.findJuegoByNombre("WWE 2K23"),
                juegoRepository.findJuegoByNombre("Terraria")
            ),
            mutableSetOf(),
            mutableSetOf(),
            mutableSetOf(),
            "Argentina",
            setOf(
                Plataformas.WINDOWS,
                Plataformas.PLAYSTATION4,
                Plataformas.XBOXONE,
                Plataformas.NINTENDOSWITCH
            ),
            mutableSetOf(
                DiaHorarioPreferido(DiaDeLaSemana.VIERNES, HorariosFavoritos.MAÑANA),
                DiaHorarioPreferido(DiaDeLaSemana.VIERNES, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.SABADO, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.DOMINGO, HorariosFavoritos.NOCHE),
            ),
            "cirrformarotom"
        )

        usuario4 = Usuario(
            "Playxel",
            "https://www.fieremostre.it/wp-content/uploads/2023/09/gaming-computer-table-video-game-room-with-neon-lighting-purple-color.jpg",
            LocalDate.of(1985, 11, 2),
            "test4@gmail.com",
            "1234",
            mutableSetOf(
                juegoRepository.findJuegoByNombre("FIFA 23"),
                juegoRepository.findJuegoByNombre("Astroneer"),
                juegoRepository.findJuegoByNombre("Left 4 Dead 2")
            ),
            mutableSetOf(),
            mutableSetOf(),
            mutableSetOf(),
            "Argentina",
            setOf(
                Plataformas.WINDOWS,
                Plataformas.XBOXONE,
                Plataformas.XBOXSERIESX
            ),
            mutableSetOf(
                DiaHorarioPreferido(DiaDeLaSemana.LUNES, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.MARTES, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.MIERCOLES, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.JUEVES, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.VIERNES, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.SABADO, HorariosFavoritos.NOCHE),
                DiaHorarioPreferido(DiaDeLaSemana.DOMINGO, HorariosFavoritos.NOCHE),
            ),
            "PlayExEl"
        )

        usuario5 = Usuario(
            "ElVendeHumo",
            "https://media.wired.com/photos/593277b144db296121d6b56f/master/w_1600%2Cc_limit/conorclarke_03.jpg",
            LocalDate.of(1996, 3, 29),
            "test5@gmail.com",
            "1234",
            mutableSetOf(
                juegoRepository.findJuegoByNombre("Counter-Strike: Global Offensive"),
                juegoRepository.findJuegoByNombre("Rocket League"),
                juegoRepository.findJuegoByNombre("Terraria"),
                juegoRepository.findJuegoByNombre("Among Us")
            ),
            mutableSetOf(),
            mutableSetOf(),
            mutableSetOf(),
            "Argentina",
            setOf(
                Plataformas.LINUX,
                Plataformas.ANDROID
            ),
            mutableSetOf(
                DiaHorarioPreferido(DiaDeLaSemana.LUNES, HorariosFavoritos.MAÑANA),
                DiaHorarioPreferido(DiaDeLaSemana.MARTES, HorariosFavoritos.MAÑANA),
                DiaHorarioPreferido(DiaDeLaSemana.MIERCOLES, HorariosFavoritos.TARDE)
            ),
            "elvendehumo123"
        )

        usuario6 = Usuario(
            "omnimrgus",
            "https://i.ytimg.com/vi/Mc-bUk5z5p4/mqdefault.jpg",
            LocalDate.of(2005, 5, 15),
            "test6@gmail.com",
            "1234",
            mutableSetOf(
                juegoRepository.findJuegoByNombre("Content Warning"),
                juegoRepository.findJuegoByNombre("Deep Rock Galactic"),
                juegoRepository.findJuegoByNombre("Among Us")
            ),
            mutableSetOf(),
            mutableSetOf(),
            mutableSetOf(),
            "Argentina",
            setOf(
                Plataformas.WINDOWS
            ),
            mutableSetOf(
                DiaHorarioPreferido(DiaDeLaSemana.SABADO, HorariosFavoritos.MAÑANA),
                DiaHorarioPreferido(DiaDeLaSemana.SABADO, HorariosFavoritos.TARDE),
                DiaHorarioPreferido(DiaDeLaSemana.SABADO, HorariosFavoritos.NOCHE)
            ),
            "noonecansaveu"
        )

        usuario1.amigos.add(usuario2)
        usuario2.amigos.add(usuario1)
        usuario3.amigos.add(usuario4)
        usuario3.amigos.add(usuario5)
        usuario4.amigos.add(usuario3)
        usuario5.amigos.add(usuario3)
        usuarioRepository.saveAll(listOf(usuario1, usuario2, usuario3, usuario4, usuario5, usuario6))
    }



    fun resenias() {
        val user1= usuarioRepository.findById(usuario1.id).get()
        val user2= usuarioRepository.findById(usuario2.id).get()
        val user3= usuarioRepository.findById(usuario3.id).get()
        val user4= usuarioRepository.findById(usuario4.id).get()
        val user5= usuarioRepository.findById(usuario5.id).get()
        val user6= usuarioRepository.findById(usuario6.id).get()

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
            3,
            "Ni me gustó ni me disgustó",
            LocalDate.of(2024, 4, 1),
            LocalTime.of(18,30)
        )
        user2.addResenia(resenia2)

        resenia3 = Resenia(
            usuario1.id,
            5,
            "Pasame la receta para ganar en el fifa",
            LocalDate.of(2024, 2, 21),
            LocalTime.of(22,30)
        )
        user3.addResenia(resenia3)

        resenia4 = Resenia(
            usuario6.id,
            2,
            "Esta mal pero no tan mal",
            LocalDate.of(2024, 1, 3),
            LocalTime.of(12,30)
        )
        user4.addResenia(resenia1)

        resenia5 = Resenia(
            usuario4.id,
            4,
            "Me cayo bien",
            LocalDate.of(2024, 5, 7),
            LocalTime.of(18,30)
        )
        user5.addResenia(resenia2)

        resenia6 = Resenia(
            usuario4.id,
            1,
            "Juega para atras, muy toxico",
            LocalDate.of(2024, 2, 17),
            LocalTime.of(22,30)
        )
        user6.addResenia(resenia3)

        reseniaRepository.saveAll(listOf(resenia1, resenia2, resenia3, resenia4, resenia5, resenia6))
        usuarioRepository.saveAll(listOf(user1, user2, user3, user4, user5, user6))
    }
}
