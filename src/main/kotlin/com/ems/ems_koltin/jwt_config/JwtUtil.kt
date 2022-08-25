package com.ems.ems_koltin.jwt_config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class JwtUtil(@Value("\${jwt.secret-code}") private val secret:String) {

    private fun extractAllClaims(token:String)=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    
    fun <T> extractClaim(token: String,claimsResolver:Function<Claims,T>):T{
        val claims:Claims=extractAllClaims(token)
        return claimsResolver.apply(claims)
    }
    
    fun extractUsername(token: String)=extractClaim(token, Claims::getSubject)
    
    fun extractExpiration(token: String): Date =extractClaim(token, Claims::getExpiration)

    fun isTokenExpired(token: String):Boolean=extractExpiration(token).before(Date())
    
    fun createToken(claims: Map<String, Object>, subject: String):String=Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis())).setExpiration(Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 10)).signWith(SignatureAlgorithm.HS256,secret).compact()

    fun generateToken(userName:String):String{
        val claims: Map<String, Object> = HashMap()
        return createToken(claims, userName)
    }

    fun validateToken(token:String, userDetails:UserDetails ):Boolean{
        val userName:String= extractUsername(token)
        return ((userName==userDetails.username) && (!isTokenExpired(token)))
    }
    
}