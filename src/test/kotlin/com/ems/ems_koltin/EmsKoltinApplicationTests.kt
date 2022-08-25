package com.ems.ems_koltin

import com.ems.ems_koltin.bean_dto.Department
import com.ems.ems_koltin.bean_dto.Employee
import com.ems.ems_koltin.entity_dao.dept_entity.DeptEntity
import com.ems.ems_koltin.entity_dao.emp_entity.EmpEntity
import com.ems.ems_koltin.entity_dao.user_entity.UserEntity
import com.ems.ems_koltin.jwt_config.CustomUserDetailsService
import com.ems.ems_koltin.repository.dept_repo.DepartmentRepo
import com.ems.ems_koltin.repository.emp_repo.EmployeeRepo
import com.ems.ems_koltin.repository.user_repo.UserRepo
import com.ems.ems_koltin.service.EmpService
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class EmsKoltinApplicationTests() {
    @Mock
    private val empRepo: EmployeeRepo? = null

    @Mock
    private val deptRepo: DepartmentRepo? = null

    @Mock
    private val userRepo: UserRepo?= null

    @Autowired
    private val passwordEncoder: PasswordEncoder?=null

    @InjectMocks
    private val empService: EmpService? = null

    @InjectMocks
    private val customUserDetailsService: CustomUserDetailsService? = null


    @Test
    fun saveEmployeeTest() {
        val employeeEntity = EmpEntity(1, "Gowtham", "9944751745".toLong(), "gowtham@gmail.com", 1)
        Mockito.`when`(empRepo!!.save(ArgumentMatchers.any(EmpEntity::class.java))).thenReturn(employeeEntity)
        val empCreated = empService!!.saveEmployee(Employee("Gowtham", "9944751745".toLong(), "gowtham@gmail.com", 1))
        val empCreated1 = empRepo.save(employeeEntity)
        AssertionsForClassTypes.assertThat((empCreated.body as EmpEntity?)!!.empName).isSameAs("Gowtham")
        Mockito.verify(empRepo).save(employeeEntity)
    }

    @Test
    fun saveDepartmentTest() {
        val deptEntity = DeptEntity(1, "HPDC")
        Mockito.`when`(deptRepo!!.save(ArgumentMatchers.any(DeptEntity::class.java))).thenReturn(deptEntity)
        val deptCreated = empService!!.saveDepartment(Department("HPDC"))
        val deptCreated1 = deptRepo.save(deptEntity)
        AssertionsForClassTypes.assertThat((deptCreated.body as DeptEntity?)!!.deptName).isSameAs("HPDC")
        Mockito.verify(deptRepo).save(deptEntity)
    }

    @Test
    fun fetchEmployeeTest() {
        val employeeEntity = EmpEntity(1, "Gowtham", "9944751745".toLong(), "gowtham@gmail.com", 1)
        Mockito.`when`(empRepo!!.findOneByempId(ArgumentMatchers.any(Int::class.java))).thenReturn(employeeEntity)
        val empCreated = empService!!.fetchEmployee(1)
        Mockito.verify(empRepo).findOneByempId(1)
    }

    @Test
    fun fetchDepartmentTest() {
        val deptEntity = DeptEntity(1, "HPDC")
        Mockito.`when`(deptRepo!!.findOneBydeptId(ArgumentMatchers.any(Int::class.java))).thenReturn(deptEntity)
        val deptCreated = empService!!.fetchDepartment(1)
        Mockito.verify(deptRepo).findOneBydeptId(1)
    }

    @Test
    fun updateEmployeeTest() {
        val employee = Employee("Ram", "9944751745".toLong(), "gowtham@gmail.com", 1)
        val employeeEntity = EmpEntity(1, "Ram", "9944751745".toLong(), "gowtham@gmail.com", 1)
        Mockito.`when`(empRepo!!.findOneByempId(ArgumentMatchers.any(Int::class.java))).thenReturn(employeeEntity)
        val res = empService!!.updateEmployee(1, employee)
        Assertions.assertEquals("Employee Id: 1 Employee Name: Ram  Update Successful", res)
    }

    @Test
    fun updateDepartmentTest() {
        val department = Department("LPDC")
        val deptEntity = DeptEntity(1, "LPDC")
        Mockito.`when`(deptRepo!!.findOneBydeptId(ArgumentMatchers.any(Int::class.java))).thenReturn(deptEntity)
        val res = empService!!.updateDepartment(1, department)
        Assertions.assertEquals("Department Id: 1 Department Name: LPDC  Update Successful", res)
    }

    @Test
    fun deleteEmployeeTest() {
        val employeeEntity = EmpEntity(1, "Ram", "9944751745".toLong(), "gowtham@gmail.com", 1)
        Mockito.`when`(empRepo!!.findOneByempId(ArgumentMatchers.any(Int::class.java))).thenReturn(employeeEntity)
        val res = empService!!.deleteEmployee(1)
        Assertions.assertEquals("Employee Id: 1 Employee Name: Ram Delete Successful", res)
    }

    @Test
    fun deleteDepatmentTest() {
        val deptEntity = DeptEntity(1, "LPDC")
        Mockito.`when`(deptRepo!!.findOneBydeptId(ArgumentMatchers.any(Int::class.java))).thenReturn(deptEntity)
        val res = empService!!.deleteDepartment(1)
        Assertions.assertEquals("Department Id: 1 Department Name: LPDC Delete Successful", res)
    }

    @Test
    fun loadUserByUsernameTest(){
        var user: UserEntity = UserEntity(101, "admin", passwordEncoder!!.encode("pwd1"), true, "ADMIN", "user1@gmail.com")
        userRepo?.save(user)
        Mockito.`when`(userRepo!!.findOneByuserName("admin")).thenReturn(user)
        userRepo!!.findOneByuserName("admin")
        Mockito.verify(userRepo!!).findOneByuserName("admin")
    }

}
