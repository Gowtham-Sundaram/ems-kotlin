package com.ems.ems_koltin.repository.dept_repo

import com.ems.ems_koltin.entity_dao.dept_entity.DeptEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepo: JpaRepository<DeptEntity, Int> {
    fun findOneBydeptId(deptId: Int): DeptEntity?
}