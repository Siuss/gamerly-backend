package com.gamerly.projectgamerly.bootstrap

import com.gamerly.projectgamerly.domain.Juego
import com.gamerly.projectgamerly.domain.HorariosFavoritos
import com.gamerly.projectgamerly.domain.Resenia
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.repos.GameRepository
import com.gamerly.projectgamerly.repos.ReviewRepository
import com.gamerly.projectgamerly.repos.UserRepository
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
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
    @Autowired
    lateinit var juegoRepository : GameRepository
    lateinit var usuario1 :Usuario
    lateinit var usuario2 : Usuario
    lateinit var usuario3 : Usuario
    lateinit var resenia1 : Resenia
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
        this.usuario()
        this.resenias()
        this.juegos()
        this.obtenerResenias()
    }

    fun usuario() {
        usuario1 = Usuario(
            "Nanami",
            "https://imagen.nextn.es/wp-content/uploads/2018/06/1807-03-Pok%C3%A9mon-GO-Squirtle-gafas-de-sol.jpg?strip=all&lossy=1&ssl=1",
            LocalDate.of(1999, 1, 1),
            mutableListOf(DiaDeLaSemana.SABADO, DiaDeLaSemana.DOMINGO),
            "test@gmail.com",
            "usuarioFafa",
            mutableListOf("lol", "terraria"),
            mutableListOf(HorariosFavoritos.NOCHE, HorariosFavoritos.TARDE),
            "Argentina",
            setOf("PC", "PS4")
        )
        usuario2 = Usuario(
            "Usuario 2",
            "https://descubre.rci.com/wp-content/uploads/2019/08/800x500px_Bariloche2_A.png",
            LocalDate.of(1999, 1, 1),
            mutableListOf(DiaDeLaSemana.SABADO, DiaDeLaSemana.DOMINGO),
            "test2@gmail.com",
            "123",
            mutableListOf("stardew valley", "overcooked"),
            mutableListOf(HorariosFavoritos.NOCHE, HorariosFavoritos.TARDE),
            "Argentina",
            setOf("PC")
        )
        usuario3 = Usuario(
            "Nicolas",
            "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/054.png",
            LocalDate.of(1999, 9, 1),
            mutableListOf(DiaDeLaSemana.VIERNES,DiaDeLaSemana.SABADO, DiaDeLaSemana.DOMINGO),
            "test1@gmail.com",
            "contrasenia",
            mutableListOf("Fifa24", "WWE 2k23, ternaria","ETS2"),
            mutableListOf(HorariosFavoritos.MAÑANA, HorariosFavoritos.NOCHE),
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

    fun juegos() {
        listaJuegos.addAll(
            listOf(
                Juego(
                    "FIFA 23",
                    "https://images.igdb.com/igdb/image/upload/t_original/sci00g.webp",
                    listOf("Windows", "PlayStation 4", "PlayStation 5", "Xbox Series X")
                ),
                Juego(
                    "Counter-Strike: Global Offensive",
                    "https://images.igdb.com/igdb/image/upload/t_original/ark8i.webp",
                    listOf("Linux", "Mac", "Windows", "PlayStation 3", "Xbox 360")
                ),
                Juego(
                    "Euro Truck Simulator 2",
                    "https://images.igdb.com/igdb/image/upload/t_original/scgqh4.webp",
                    listOf("Linux", "Mac", "Windows")
                ),
                Juego(
                    "Pummel Party",
                    "https://images.igdb.com/igdb/image/upload/t_original/arqdt.webp",
                    listOf("Nintendo Switch", "Windows", "PlayStation 4", "Xbox One")
                ),
                Juego(
                    "Rocket League",
                    "https://images.igdb.com/igdb/image/upload/t_original/k1hcu9flbu0tvheine15.webp",
                    listOf("Linux", "Mac", "Nintendo Switch", "Windows", "PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X")
                ),
                Juego(
                    "Lethal Company",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar1rug.webp",
                    listOf("Windows")
                ),
                Juego(
                    "Content Warning",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar2v8v.webp",
                    listOf("Windows")
                ),
                Juego(
                    "Terraria: Journey's End",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar2voa.webp",
                    listOf("Android", "iOS", "Linux", "Mac", "Nintendo Switch", "PC (Microsoft Windows)", "PlayStation 4", "Xbox One", "Xbox Series X")
                ),
                Juego(
                    "Overcooked!",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar6xz.webp",
                    listOf("Windows", "PlayStation 4", "Xbox One")
                ),
                Juego(
                    "Astroneer",
                    "https://images.igdb.com/igdb/image/upload/t_original/sci95s.webp",
                    listOf("Nintendo Switch", "Windows", "PlayStation 4", "Xbox One")
                ),
                Juego(
                    "Left 4 Dead 2",
                    "https://images.igdb.com/igdb/image/upload/t_original/arq5o.webp",
                    listOf("Linux", "Mac", "Windows", "Xbox 360")
                ),
                Juego(
                    "Project Zomboid",
                    "https://images.igdb.com/igdb/image/upload/t_original/ihmtywjhagxtdzzf1wrw.webp",
                    listOf("Linux", "Mac", "Windows")
                ),
                Juego(
                    "Stardew Valley",
                    "https://images.igdb.com/igdb/image/upload/t_original/sw7rtba7p1xs77klsime.webp",
                    listOf("Android", "iOS", "Linux", "Mac", "Nintendo Switch", "Windows", "PlayStation 4", "PlayStation Vita", "Wii U", "Xbox One")
                ),
                Juego(
                    "Deep Rock Galactic",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar4nr.webp",
                    listOf("Windows", "PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X")
                ),
                Juego(
                    "Among Us",
                    "https://images.igdb.com/igdb/image/upload/t_original/ar7tq.webp",
                    listOf("Android", "iOS", "Nintendo Switch", "Windows", "PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X")
                ),
                Juego(
                    "Phasmophobia",
                    "https://images.igdb.com/igdb/image/upload/t_original/ardfr.webp",
                    listOf("Oculus Rift", "Windows", "PlayStation 5", "PlayStation VR2", "SteamVR", "Windows Mixed Reality", "Xbox Series X")
                ),
                Juego(
                    "Dead by Daylight",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc6vlh.webp",
                    listOf("Nintendo Switch", "Windows", "PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X")
                ),
                Juego(
                    "Sonic & All-Stars Racing Transformed",
                    "https://images.igdb.com/igdb/image/upload/t_original/j679caufkftsns3n26df.webp",
                    listOf("Android", "iOS", "Windows", "PlayStation 3", "PlayStation Vita", "Wii U", "Xbox 360")
                )
            )
        )
        juegoRepository.saveAll(listaJuegos)
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
