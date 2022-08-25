package com.ems.ems_koltin.jwt_config

import com.ems.ems_koltin.entity_dao.user_entity.UserEntity
import com.ems.ems_koltin.repository.user_repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(@Autowired val userRepo:UserRepo): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): UserDetails {
        val userEntity: UserEntity? = userRepo.findOneByuserName(userName)
            return org.springframework.security.core.userdetails.User(userEntity?.userName,userEntity?.password,
                userEntity?.let { getAuthority(it) })
    }
    private fun getAuthority(userEntity: UserEntity): Set<SimpleGrantedAuthority> {
        val authorities: MutableSet<SimpleGrantedAuthority> = HashSet<SimpleGrantedAuthority>()
        authorities.add(SimpleGrantedAuthority("ROLE_" + userEntity.role))
        /*
		 * user.getRole().forEach(role -> { authorities.add(new
		 * SimpleGrantedAuthority("ROLE_" + role.getName())); });
		 */return authorities
    }
}