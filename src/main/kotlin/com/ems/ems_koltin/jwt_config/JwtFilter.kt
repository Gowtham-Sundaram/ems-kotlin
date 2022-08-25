package com.ems.ems_koltin.jwt_config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(@Autowired private val jwtUtil:JwtUtil, @Autowired private val customUserDetailsService: CustomUserDetailsService): OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader:String? = httpServletRequest.getHeader("Authorization")

        var token:String?=null
        var userName:String?=null

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            token=authorizationHeader.substring(7)
            userName=jwtUtil.extractUsername(token)
        }
        if(userName!=null && SecurityContextHolder.getContext().authentication==null){
           var userDetails: UserDetails =customUserDetailsService.loadUserByUsername(userName)
            if(token?.let{ jwtUtil.validateToken(it, userDetails) } == true){
                var usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken=UsernamePasswordAuthenticationToken(userDetails,null,userDetails.authorities)
                usernamePasswordAuthenticationToken.details=WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                SecurityContextHolder.getContext().authentication=usernamePasswordAuthenticationToken
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse)
        }


}