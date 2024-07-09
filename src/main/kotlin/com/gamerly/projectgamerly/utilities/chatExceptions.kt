package com.gamerly.projectgamerly.utilities

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST)
internal class ChatYaExiste(msg : String) : RuntimeException(msg)

@ResponseStatus(code = HttpStatus.NOT_FOUND)
internal class ChatNoExiste(msg : String) : RuntimeException(msg)