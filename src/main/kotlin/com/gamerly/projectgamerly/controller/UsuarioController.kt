package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.domain.HorariosFavoritos
import com.gamerly.projectgamerly.domain.Usuario
import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.resources.enum.DiaDeLaSemana
import com.gamerly.projectgamerly.service.BloqueosService
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin(origins = ["*"])
class UsuarioController {
    @Autowired
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var bloqueosService: BloqueosService



    @PostMapping("/buscar/{idJuego}")
    fun busquedaAvanzada(@RequestBody inputBusqueda: InputBusquedaDTO, @PathVariable idJuego: Long): List<UsuarioBusquedaDto> {
        return usuarioService.busquedaAvanzada(inputBusqueda, idJuego)
    }

    @PutMapping("/token/{id}")
    fun actualizarToken(
        @PathVariable id: Long,
        @RequestBody request: ExpoTokenRequest
    ): Boolean {
        return (usuarioService.actualizarTokenNotificaciones(id, request.expoPushToken))
    }


    @PostMapping("/solicitud-clave/{email}")
    fun solicitudClave(@PathVariable email: String): String {
        return usuarioService.solicitarClave(email)
    }

    @PostMapping("/verificar-codigo-recuperacion/{token}")
    fun verificarTokenDeRecuperacion(@PathVariable token: String): UsuarioLoginDTO {
        return UsuarioLoginDTO.from(usuarioService.verificarTokenDeRecuperacion(token))
    }

    @PostMapping("/nueva-clave")
    fun nuevaClave(@RequestBody body: NuevaClaveDto): String {
        return usuarioService.nuevaClave(body.email, body.contrasenia).email
    }

    @GetMapping("/detalle/{idUsuario}")
    fun detalleUsuario(@PathVariable idUsuario: Long): UsuarioDetalleDTO {
        val usuario = usuarioService.getUsuario(idUsuario)
        return UsuarioDetalleDTO(usuario, usuario.resenias.map{usuarioService.conversionReseniaDTO(it)})
    }

    @GetMapping("/detalle-edicion/{idUsuario}")
    fun detalleEdicionUsuario(@PathVariable idUsuario: Long): UsuarioDetalleEdicionDTO {
        val usuario = usuarioService.getUsuario(idUsuario)
        return UsuarioDetalleEdicionDTO(usuario)
    }

    @PutMapping("/perfil")
    fun actualizarPerfil(@RequestBody perfil: UsuarioDetalleEdicionDTO): UsuarioDetalleEdicionDTO {
        val usuario = usuarioService.actualizarPerfil(perfil)
        return UsuarioDetalleEdicionDTO(usuario)
    }

    @GetMapping("/comentarios/{idUsuario}")
    fun comentariosUsuario(@PathVariable idUsuario: Long) : List<ReseniasDTO>{
        return usuarioService.comentariosUsuario(idUsuario)
    }

    @GetMapping("/")
    fun getAllUsers(): List<UsuarioDetalleDTO> {
        return usuarioService.getAllUsers()
    }

    @DeleteMapping("/usuarios/{idUsuario}")
    fun deleteUsuario(@PathVariable idUsuario: Long): UsuarioLoginDTO {
        return UsuarioLoginDTO.from(usuarioService.deleteUsuario(idUsuario))
    }

    @PatchMapping("/editar/{idUsuario}")
    fun editarUsuarioDatos(@PathVariable idUsuario: Long, @RequestBody usuarioEditado: UsuarioEditarDTO): UsuarioDetalleDTO {
        return usuarioService.editarUsuario(idUsuario, usuarioEditado)
    }

    @GetMapping("/jugadoresPorJuego/{idJuego}")
    fun traerUsuariosPorJuego(@PathVariable idJuego: Long): List<UsuarioBusquedaJuegosDTO> {
        return usuarioService.getUsuarioPorJuego(idJuego)
    }

    // Trae los amigos de un usuario a partir del id del usuario
    @GetMapping("/amigos/{idUsuario}")
    fun getAmigosDelUsuario(@PathVariable idUsuario: Long, @RequestParam(required = false) bloqueados: Boolean): List<AmigoDTO> {
        return usuarioService.getAmigosDelUsuario(idUsuario, bloqueados)
    }

    // Borrar un amigo del usuario
    @PostMapping("/{idUsuario}/amigos/{idAmigo}")
    fun deleteAmigoDelUsuario(@PathVariable idUsuario: Long, @PathVariable idAmigo: Long):AmigoDTO {
        return AmigoDTO.from(usuarioService.deleteAmigoDelUsuario(idUsuario, idAmigo))
    }

    // Bloquear un usuario
    @PostMapping("/{idUsuario}/bloquear/{idBloqueado}")
    fun bloquearUsuario(@PathVariable idUsuario: Long, @PathVariable idBloqueado: Long): UsuarioBloqueadoDTO {
        return UsuarioBloqueadoDTO.from(bloqueosService.bloquearUsuario(idUsuario, idBloqueado))
    }

    // Desbloquear un usuario
    @PostMapping("/{idUsuario}/desbloquear/{idBloqueado}")
    fun desbloquearUsuario(@PathVariable idUsuario: Long, @PathVariable idBloqueado: Long): UsuarioBloqueadoDTO {
        return UsuarioBloqueadoDTO.from(bloqueosService.desbloquearUsuario(idUsuario, idBloqueado))
    }

    @GetMapping("{idUsuarioLogueado}/esta-bloqueado/{idUsuario}")
    fun getUsuarioEstaBloqueado(@PathVariable idUsuarioLogueado: Long, @PathVariable idUsuario: Long): Boolean {
        return bloqueosService.getUsuarioEstaBloqueado(idUsuarioLogueado, idUsuario)
    }

    @GetMapping("/bloqueados/{idUsuario}")
    fun getUsuariosBloqueados(@PathVariable idUsuario: Long): List<UsuarioBloqueadoDTO> {
        return bloqueosService.getUsuariosBloqueados(idUsuario).map{UsuarioBloqueadoDTO.from(it)}
    }

    @GetMapping("/usuarios")
    fun getUsuariosFiltrados(
        @RequestParam(required = false) puntaje: Long?,
        @RequestParam(required = false) dias: List<DiaDeLaSemana>?,
        @RequestParam(required = false) horarios: List<HorariosFavoritos>?,
        @RequestParam(required = false) nombre: String?
    ): List<UsuarioDetalleDTO> {
        return usuarioService.getUsuariosFiltrados(puntaje, dias, horarios, nombre)
    }


}
