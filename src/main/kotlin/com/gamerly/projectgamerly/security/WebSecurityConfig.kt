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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

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
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(  "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html").permitAll()
                it.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                it.requestMatchers("/error").permitAll()
                it.requestMatchers(HttpMethod.POST, "/auth/user").permitAll()
                it.requestMatchers(HttpMethod.POST, "/auth/oauth").permitAll()
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


    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        config.allowedOriginPatterns = listOf("http://localhost:*")
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
        config.allowedHeaders = listOf("*") // Permitir todos los headers
        config.allowCredentials = true // Permitir credenciales

        source.registerCorsConfiguration("/**", config)
        return source
    }

    @Bean
    fun corsFilter(): CorsFilter {
        return CorsFilter(corsConfigurationSource())
    }
}