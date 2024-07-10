package com.gamerly.projectgamerly.utilities

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
internal class ReseniaNotFound (msg : String) : RuntimeException(msg)

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
internal class ReseniaException (msg : String) : RuntimeException(msg)
