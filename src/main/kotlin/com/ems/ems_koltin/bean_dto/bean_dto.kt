package com.ems.ems_koltin.bean_dto

data class Employee(
    val empName:String,
    val mobileNo:Long,
    val mailId:String,
    val deptId:Int
)
data class Department(
    val deptName:String
)

data class AuthRequest(
    val userName:String,
    val password:String
)