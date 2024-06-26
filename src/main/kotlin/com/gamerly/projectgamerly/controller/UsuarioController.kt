package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class UsuarioController {
    @Autowired
    lateinit var usuarioService: UsuarioService

    @PostMapping("/sign-up")
    fun crearUsuario(@RequestBody usuarioNuevo: UsuarioCreacionDTO) {
        usuarioService.crearUsuario(usuarioNuevo)
    }

    @GetMapping("/buscar")
    fun busquedaAvanzada(@RequestBody inputBusqueda: InputBusquedaDTO): List<UsuarioBusquedaDto> {
        return usuarioService.busquedaAvanzada(inputBusqueda)
    }

    @PostMapping("/login")
    fun loginUsuario(@RequestBody credenciales: CredencialesDTO): UsuarioLoginDTO {
        return usuarioService.login(credenciales)
    }

    @GetMapping("/detalle/{idUsuario}")
    fun detalleUsuario(@PathVariable idUsuario: Long): UsuarioDetalleDTO {
        val usuario = usuarioService.getUsuario(idUsuario)

        val primerResenia = usuario.resenias.firstOrNull()
        if(primerResenia != null) {
            val primerReseniaDTO = usuarioService.conversionReseniaDTO(primerResenia)
            return UsuarioDetalleDTO(usuario, primerReseniaDTO)
        }
        return UsuarioDetalleDTO(usuario, null)
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

    @ExceptionHandler(Exception::class)
    fun excepcionGenerica(exception: Exception): ResponseEntity<HashMap<String, Any>> {
        val entity = hashMapOf<String, Any>()
        exception.message?.let { entity.put("message", it) }
        return ResponseEntity(entity, HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/jugadoresPorJuego/{idJuego}")
    fun traerUsuariosPorJuego(@PathVariable idJuego: Long): List<UsuarioBusquedaJuegosDTO> {
        return usuarioService.getUsuarioPorJuego(idJuego)
    }

    // Trae los amigos de un usuario a partir del id del usuario
    @GetMapping("/amigos/{idUsuario}")
    fun getAmigosDelUsuario(@PathVariable idUsuario: Long): List<AmigoDTO> {
        return usuarioService.getAmigosDelUsuario(idUsuario).map{AmigoDTO.from(it)}
    }

    // Borrar un amigo del usuario
    @PostMapping("/{idUsuario}/amigos/{idAmigo}")
    fun deleteAmigoDelUsuario(@PathVariable idUsuario: Long, @PathVariable idAmigo: Long):AmigoDTO {
        return AmigoDTO.from(usuarioService.deleteAmigoDelUsuario(idUsuario, idAmigo))
    }
}
