package com.gamerly.projectgamerly.bootstrap

import com.gamerly.projectgamerly.ProjectGamerlyApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

class ServletInitializer : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(ProjectGamerlyApplication::class.java)
    }
}