package com.gamerly.projectgamerly.controller

import com.gamerly.projectgamerly.dtos.AuthDTO
import com.gamerly.projectgamerly.security.TokenUtils
import com.gamerly.projectgamerly.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["**"])
@RequestMapping("/auth")
class AuthController {

    @Autowired lateinit var authService: AuthService

    @Autowired lateinit var tokenUtils: TokenUtils

    @PostMapping("/login")
    fun loginAuth (@RequestBody authDTO : AuthDTO) : String {
        authService.login(authDTO)
        return tokenUtils.createToken(authDTO.email)
    }
}