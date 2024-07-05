package com.gamerly.projectgamerly.utils

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
internal class ReseniaPendienteNotFound(msg : String) : RuntimeException(msg)
