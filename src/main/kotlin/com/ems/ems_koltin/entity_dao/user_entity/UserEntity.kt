package com.ems.ems_koltin.entity_dao.user_entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "usertb")
data class UserEntity(
	@Id
	var userId: Int=0,
	var userName: String="",
	var password: String="",
	var enabled: Boolean=false,
	var role: String="",
	var email: String=""
)