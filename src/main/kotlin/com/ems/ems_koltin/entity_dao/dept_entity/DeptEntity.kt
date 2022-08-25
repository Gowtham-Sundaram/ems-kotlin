package com.ems.ems_koltin.entity_dao.dept_entity

import javax.persistence.*

@Entity
@Table(name="Department_Table")
class DeptEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var deptId:Int=0,
    var deptName:String=""
)