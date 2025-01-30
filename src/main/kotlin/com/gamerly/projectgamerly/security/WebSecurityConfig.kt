package com.gamerly.projectgamerly.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Autowired
    lateinit var authConfiguration: AuthenticationConfiguration

    @Autowired
    lateinit var jwtAuthorizationFilter: JWTAuthorizationFilter

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(): AuthenticationManager? {
        return authConfiguration.getAuthenticationManager()
    }

    @Bean
    fun filterChain(httpSecurity: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        return httpSecurity
            .cors { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                it.requestMatchers("/error").permitAll()
                it.requestMatchers(HttpMethod.POST, "/auth/user").permitAll()
                it.requestMatchers(HttpMethod.DELETE, "/auth/user/**").permitAll()
                    .anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())
            .sessionManagement { configurer ->
                configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling(Customizer.withDefaults())
            .build()
    }
}