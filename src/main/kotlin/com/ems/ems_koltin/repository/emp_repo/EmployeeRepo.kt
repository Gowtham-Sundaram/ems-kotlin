package com.ems.ems_koltin.repository.emp_repo

import com.ems.ems_koltin.entity_dao.emp_entity.EmpEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepo: JpaRepository<EmpEntity, Int> {
    fun findOneByempId(empId:Int): EmpEntity?
}