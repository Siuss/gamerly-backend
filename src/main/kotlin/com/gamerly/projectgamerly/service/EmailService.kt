package com.gamerly.projectgamerly.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailService {
    @Autowired
    private lateinit var emailSender: JavaMailSender

    fun enviarMail(
        to: String?, asunto: String?, texto: String?
    ) {
        val message = SimpleMailMessage()
        message.from = "spazosrubio@estudiantes.unsam.edu.ar"
        message.setTo(to)
        message.subject = asunto
        message.text = texto
        emailSender.send(message)
    }
}