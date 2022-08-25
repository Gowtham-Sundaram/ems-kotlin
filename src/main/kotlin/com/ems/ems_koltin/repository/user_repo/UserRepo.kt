package com.ems.ems_koltin.repository.user_repo

import com.ems.ems_koltin.entity_dao.user_entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo:JpaRepository<UserEntity,Int> {
    fun findOneByuserName(userName: String): UserEntity?
}

