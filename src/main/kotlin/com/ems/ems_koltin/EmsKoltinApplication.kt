package com.ems.ems_koltin

import com.ems.ems_koltin.entity_dao.user_entity.UserEntity
import com.ems.ems_koltin.repository.user_repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.annotation.PostConstruct

@SpringBootApplication
class EmsKoltinApplication(@Autowired
                           private val userRepo: UserRepo, @Autowired
var passwordEncoder: PasswordEncoder){

    @PostConstruct
    fun initUsers() {
        var user: UserEntity = UserEntity(101, "admin", passwordEncoder.encode("pwd1"), true, "ADMIN", "user1@gmail.com")
        userRepo?.save(user)
        user= UserEntity(102, "manager", passwordEncoder.encode("pwd2"), true, "USER", "user2@gmail.com")
        userRepo?.save(user)
    }

}
fun main(args: Array<String>) {
    runApplication<EmsKoltinApplication>(*args)
}


