package com.gamerly.projectgamerly.domain

import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import com.gamerly.projectgamerly.utils.InvalidEmail
import com.gamerly.projectgamerly.utils.InvalidFields
import com.gamerly.projectgamerly.utils.InvalidPassword
import com.gamerly.projectgamerly.utils.PasswordMismatch
import com.gamerly.projectgamerly.domain.DiaHorarioPreferido
import com.gamerly.projectgamerly.exceptions.CredencialesInvalidasException
import jakarta.persistence.*
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "usuarios")
class Usuario(
    @Column(nullable = false)
    var nombre: String = "",

    @Column(nullable = false)
    var foto: String = "",

    @Column(name = "fecha_de_nacimiento", nullable = false)
    var fechaDeNacimiento: LocalDate = LocalDate.now(),

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false)
    var tokenNotificaciones: String = "",

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

    @ManyToMany
    @JoinTable(
        name = "amigos",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "amigo_id")]
    )
    var amigos: MutableSet<Usuario> = mutableSetOf(),

    @Column(nullable = false)
    var nacionalidad: String = "",

    @ElementCollection(targetClass = Plataformas::class)
    @CollectionTable(name = "usuario_plataformas", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "plataforma")
    var plataformas: Set<Plataformas> = mutableSetOf(),

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "diasHorariosPreferidos")
    var diasHorariosPreferidos: MutableSet<DiaHorarioPreferido> = mutableSetOf(),

    @Column(nullable = false)
    var discord: String = ""
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var resenias: MutableSet<Resenia> = mutableSetOf()

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var reseniasPendientes: MutableSet<Resenia> = mutableSetOf()

    @Column
    var fechaRecuperacionClave: LocalDateTime? = null

    @Column
    var tokenRecuperacion: String? = null

    @Column
    var shadowBan: Boolean? = false

    @ManyToMany
    var bloqueados: MutableSet<Usuario> = mutableSetOf()

    var ultimoLogin: LocalDateTime? = null

    fun addResenia(resenia: Resenia)  {
        resenias.add(resenia)
    }

    fun addReseniaPendiente(resenia: Resenia)  {
        reseniasPendientes.add(resenia)
    }

    fun removeReseniaPendienteById(reseniaId: Long)  {
        reseniasPendientes.removeIf{it.id == reseniaId}
    }

    fun calculoPuntaje(): Long {
        if (resenias.isNotEmpty()) {
            return resenias.map { it.puntaje }.average().toLong()
        } else {
            return 0
        }
    }

    fun validar(): Boolean {
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


    /**
     * Valida las credenciales del usuario
     * ttps://www.baeldung.com/java-password-hashing
     */
    fun validarCredenciales(passwordAVerificar: String) {
            if (!getDefaultEncoder().matches(passwordAVerificar, password)) {
            throw CredencialesInvalidasException()
        }
    }

    /**
     * Obtiene el encoder por defecto
     */
    private fun getDefaultEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()!!
    }


    /**
     * Crea un hash de la contraseña del usuario
     */
    fun crearPassword(rawPassword: String) {
        password = getDefaultEncoder().encode(rawPassword)
    }

    fun loguearse() {
        ultimoLogin = LocalDateTime.now()
    }

    fun actualizarTokenNotificaciones(nuevoToken: String) {
        tokenNotificaciones = nuevoToken
    }
}



