package com.gamerly.projectgamerly.utilities

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
internal class ReporteYaExiste(msg : String) : RuntimeException(msg)

@ResponseStatus(code = HttpStatus.NOT_FOUND)
internal class ReporteNoExiste(msg : String) : RuntimeException(msg)