package com.ems.ems_koltin.jwt_config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
//@EnableWebSecurity //All working as expected even without @EnableWebSecurity
class SecurityConfig(@Autowired val userDetailsService: CustomUserDetailsService, @Autowired val jwtFilter:JwtFilter) {
    @Bean
    fun passwordEncoder(): PasswordEncoder= BCryptPasswordEncoder()

    @Bean
    fun authProvider(): DaoAuthenticationProvider {
        var authProvider:DaoAuthenticationProvider= DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider;
    }
    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager=authenticationConfiguration.authenticationManager

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        /*val authenticationManagerBuilder:AuthenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)

        authenticationManagerBuilder.authenticationProvider(authProvider())

        //authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
        var authenticationManager: AuthenticationManager =authenticationManagerBuilder.build()*/

        http.csrf().disable().authorizeRequests().antMatchers("/ems/authenticate", "/h2-console/**", "/actuator/**").permitAll().antMatchers("/emp/deleteEmp/**").hasAnyRole("ADMIN").anyRequest().authenticated().and()
            .exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.headers().frameOptions().disable()

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**")
        }
    }


}