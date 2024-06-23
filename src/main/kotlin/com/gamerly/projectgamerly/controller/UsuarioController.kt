package com.gamerly.projectgamerly.controller;

import com.gamerly.projectgamerly.dtos.*
import com.gamerly.projectgamerly.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
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
        return usuarioService.getUsuarioDetalle(idUsuario)
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
    fun deleteUsuario(@PathVariable idUsuario: Long): UsuarioDetalleDTO {
        return usuarioService.deleteUsuario(idUsuario)
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
    fun traerUsuariosPorJuego(@PathVariable idJuego: Long): List<UsuarioBusquedaDto> {
        return usuarioService.getUsuarioPorJuego(idJuego)
    }

    @PostMapping("/agregar-amigo")
    fun agregarAmigo(@RequestBody agregarAmigoDTO: AgregarAmigoDTO): ResponseEntity<Any> {
        try {
            val usuarioDetalle = usuarioService.agregarAmigo(agregarAmigoDTO.idUsuario, agregarAmigoDTO.idAmigo)
            return ResponseEntity.ok(usuarioDetalle)
        } catch (ex: Exception) {
            val entity = hashMapOf<String, Any>()
            entity["message"] = ex.message ?: "Error al agregar amigo"
            return ResponseEntity(entity, HttpStatus.BAD_REQUEST)
        }
    }
}
