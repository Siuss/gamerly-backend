package com.gamerly.projectgamerly.controller

import com.gamerly.projectgamerly.dtos.AuthDTO
import com.gamerly.projectgamerly.dtos.UsuarioCreacionDTO
import com.gamerly.projectgamerly.dtos.UsuarioDetalleDTO
import com.gamerly.projectgamerly.security.TokenUtils
import com.gamerly.projectgamerly.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS])
@RequestMapping("/auth")
class AuthController {

    @Autowired lateinit var authService: AuthService

    @Autowired lateinit var tokenUtils: TokenUtils

    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Permite el login de un usuario usando jwt como token de autenticación")
    fun loginAuth (@RequestBody authDTO : AuthDTO) : String {
        val user = authService.login(authDTO)
        return tokenUtils.createToken(user.email, user.id)
    }

    @PostMapping("/user")
    @Operation(summary = "Creacion de usuario", description = "Permite crear usuarios a la app")
    fun crear(@RequestBody crendentialUser : UsuarioCreacionDTO) = authService.crearUsuario(crendentialUser)


}