package com.ems.ems_koltin.entity_dao.emp_entity

import javax.persistence.*


@Entity
@Table(name="Employee_Table")
class EmpEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var empId:Int=0,
    var empName:String="",
    var mobileNo:Long=0,
    var mailId:String="",
    var deptId:Int=0
)
