package com.gamerly.projectgamerly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ProjectGamerlyApplication
fun main(args: Array<String>) {
	runApplication<ProjectGamerlyApplication>(*args)
}
