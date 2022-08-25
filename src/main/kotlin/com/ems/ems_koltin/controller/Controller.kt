package com.ems.ems_koltin.controller

import com.ems.ems_koltin.bean_dto.AuthRequest
import com.ems.ems_koltin.bean_dto.Department
import com.ems.ems_koltin.bean_dto.Employee
import com.ems.ems_koltin.jwt_config.JwtUtil
import com.ems.ems_koltin.service.EmpService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/ems")
class Controller(
        @Autowired private val service: EmpService,
        @Autowired private val jwtUtil: JwtUtil,
        @Autowired private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/authenticate")
    @Throws(Exception::class)
    fun generateToken(@RequestBody authRequest: AuthRequest): String? {
        try {

            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(authRequest.userName, authRequest.password)
            )
        } catch (ex: Exception) {
            //throw Exception("invalid username/password")
            return "invalid username/password"
        }
        return jwtUtil.generateToken(authRequest.userName)
    }

    @PostMapping("/saveEmp")
    fun saveEmployee(@RequestBody employee: Employee):
            ResponseEntity<Any> = service.saveEmployee(employee)

    @GetMapping("getEmp/{empId}")
    fun fetchEmployee(@PathVariable empId: Int): ResponseEntity<Any> =
            service.fetchEmployee(empId) ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "This employee does not exist"
            )

    @PutMapping("updateEmp/{empId}")
    fun updateEmployee(@PathVariable empId: Int, @RequestBody employee: Employee): String =
            service.updateEmployee(empId, employee)

    @DeleteMapping("deleteEmp/{empId}")
    fun deleteEmployee(@PathVariable empId: Int): String = service.deleteEmployee(empId)

    @PostMapping("/saveDept")
    fun saveDepartment(@RequestBody department: Department): ResponseEntity<Any> = service.saveDepartment(department)

    @GetMapping("getDept/{deptId}")
    fun fetchDepartment(@PathVariable deptId: Int): ResponseEntity<Any> =
            service.fetchDepartment(deptId) ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "This department does not exist"
            )

    @PutMapping("updateDept/{deptId}")
    fun updateDepartment(@PathVariable deptId: Int, @RequestBody department: Department): String =
            service.updateDepartment(deptId, department)

    @DeleteMapping("deleteDept/{deptId}")
    fun deleteDepartment(@PathVariable deptId: Int): String = service.deleteDepartment(deptId)
}