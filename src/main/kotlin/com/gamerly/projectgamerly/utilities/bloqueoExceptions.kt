package com.gamerly.projectgamerly.utilities

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
internal class NoSePuedeBloquearASiMismo(msg : String) : RuntimeException(msg)
