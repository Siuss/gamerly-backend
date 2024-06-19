package com.gamerly.projectgamerly.utils

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
internal class InvalidEmail(msg : String) : RuntimeException(msg)

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
internal class InvalidPassword (msg : String) : RuntimeException(msg)

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
internal class InvalidFields (msg : String) : RuntimeException(msg)

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
internal class PasswordMismatch (msg : String) : RuntimeException(msg)

@ResponseStatus(code = HttpStatus.NOT_FOUND)
internal class userNotFound (msg: String) : RuntimeException(msg)

