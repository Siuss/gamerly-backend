package com.gamerly.projectgamerly.domain

import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import com.gamerly.projectgamerly.utils.InvalidEmail
import com.gamerly.projectgamerly.utils.InvalidFields
import com.gamerly.projectgamerly.utils.InvalidPassword
import com.gamerly.projectgamerly.utils.PasswordMismatch
import com.gamerly.projectgamerly.domain.DiaHorarioPreferido
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "usuarios")
class Usuario(
    @Column(nullable = false)
    var nombre: String = "",

    var discord: String = "",

    @Column(nullable = false)
    var foto: String = "",

    @Column(name = "fecha_de_nacimiento", nullable = false)
    var fechaDeNacimiento: LocalDate = LocalDate.now(),

    @ElementCollection(targetClass = DiaDeLaSemana::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_dia_favorito", joinColumns = [JoinColumn(name = "usuario_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_favorito")
    var diaFavorito: MutableSet<DiaDeLaSemana> = mutableSetOf(),

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @ManyToMany
    @CollectionTable(name = "usuario_juegos_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "juego_preferido")
    var juegosPreferidos: MutableSet<Juego> = mutableSetOf(),

    @OneToMany
    @CollectionTable(name = "usuario_solicitud_enviada", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "solicitud_enviada")
    var solicitudesEnviadas: MutableSet<Solicitud> = mutableSetOf(),

    @OneToMany
    @CollectionTable(name = "usuario_solicitud_recibida", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "solicitud_recibida")
    var solicitudesRecibidas: MutableSet<Solicitud> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "amigos",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "amigo_id")]
    )
    var amigos: MutableSet<Usuario> = mutableSetOf(),

    @ElementCollection(targetClass = HorariosFavoritos::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_dias_horarios_preferidos", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "dia_horario_preferido")
    var horariosPreferidos: MutableList<HorariosFavoritos> = mutableListOf(),

    @Column(nullable = false)
    var nacionalidad: String = "",

    @ElementCollection(targetClass = Plataformas::class)
    @CollectionTable(name = "usuario_plataformas", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "plataforma")
    var plataformas: Set<Plataformas> = mutableSetOf(),

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "usuario_id")
    var diasHorariosPreferidos: MutableList<DiaHorarioPreferido> = mutableListOf()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var resenias: MutableSet<Resenia> = mutableSetOf()

    init {
        generarDiasHorariosPreferidos()
    }

    private fun generarDiasHorariosPreferidos() {
        diaFavorito.zip(horariosPreferidos).forEach { (dia, horario) ->
            diasHorariosPreferidos.add(DiaHorarioPreferido(dia, horario))
        }
    }

    fun addResenia(resenia: Resenia)  {
         resenias.add(resenia)
    }

    fun camposValidos(): Boolean {
        return validateEmail() && validatePassword() && camposVacios() && validatePasswordMatch(password)

    }

    fun validateEmail(): Boolean {

        if (!email.contains(".")) {
            throw InvalidEmail("El email no es válido")
        } else {
            return true
        }
    }

    fun validatePassword(): Boolean {
        if (password.length < 8) {
            throw InvalidPassword("La contraseña debe tener al menos 8 caracteres")
        } else {
            return true
        }
    }

    fun camposVacios(): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            throw InvalidFields("Los campos no pueden estar vacíos")
        } else {
            return true
        }
    }

    fun validatePasswordMatch(repeatPassword: String) : Boolean {
        if (password == repeatPassword) {
            return true
        } else {
            throw PasswordMismatch("Las contraseñas no coinciden")
        }
    }
}



