package com.gamerly.projectgamerly.utils

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
internal class SolicitudNotFound(msg : String) : RuntimeException(msg)


