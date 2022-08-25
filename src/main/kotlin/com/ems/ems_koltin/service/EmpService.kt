package com.ems.ems_koltin.service

import com.ems.ems_koltin.bean_dto.Department
import com.ems.ems_koltin.bean_dto.Employee
import com.ems.ems_koltin.entity_dao.dept_entity.DeptEntity
import com.ems.ems_koltin.entity_dao.emp_entity.EmpEntity
import com.ems.ems_koltin.repository.dept_repo.DepartmentRepo
import com.ems.ems_koltin.repository.emp_repo.EmployeeRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import java.text.SimpleDateFormat
import java.util.*


@Service
class EmpService(@Autowired private val empRepo: EmployeeRepo, @Autowired private val deptRepo: DepartmentRepo) {


    var logger: Logger = LoggerFactory.getLogger(EmpService::class.java)

    private fun dateInfo(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = Date()
        return formatter.format(date)
    }
    fun saveEmployee(@RequestBody employee: Employee): ResponseEntity<Any> {
        var empEntity = EmpEntity()
        BeanUtils.copyProperties(employee, empEntity)
        return try {
            empEntity = empRepo.save(empEntity)
            logger.info("Employee name ${empEntity.empName} saved @ ${dateInfo()}")
            ResponseEntity<Any>(empEntity, HttpStatus.CREATED)
        } catch (e: Exception) {
            logger.error("Unable to save user @ ${dateInfo()}, error occurred saveEmployee in service")
            ResponseEntity<Any>(StringBuilder("Unable to save employee"), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    fun fetchEmployee(@PathVariable empId: Int): ResponseEntity<Any>? {
        var empEntity: EmpEntity?=null
        return try {
            empEntity = empRepo.findOneByempId(empId)!!
            logger.info("empId ${empEntity?.empId} is accessed @ ${dateInfo()}")
            ResponseEntity<Any>(empEntity, HttpStatus.FOUND)
        } catch (e: Exception) {
            logger.warn("Wrong empId $empId entered to access @ ${dateInfo()}")
            ResponseEntity<Any>(StringBuilder("No Emp Found for given empId"), HttpStatus.NOT_FOUND)
        }
    }

    fun updateEmployee(@PathVariable empId: Int, @RequestBody employee: Employee): String {
        var empEntity: EmpEntity?=null
        return if (fetchEmployee(empId)?.body is EmpEntity) {
            empEntity = fetchEmployee(empId)?.body as EmpEntity
            BeanUtils.copyProperties(employee, empEntity)
            empRepo.save(empEntity)
            logger.info("Employee name ${employee.empName} is updated @ ${dateInfo()}")
            "Employee Id: ${empEntity.empId} Employee Name: ${empEntity.empName}  Update Successful"
        } else {
            logger.info("Employee name ${employee.empName} is not found to update @ ${dateInfo()}")
            "Employee does not exist"
        }
    }
    fun deleteEmployee(@PathVariable empId: Int): String {
        var empEntity: EmpEntity?
        return if (fetchEmployee(empId)?.body is EmpEntity) {
            empEntity = fetchEmployee(empId)?.body as EmpEntity
            empRepo.delete(empEntity)
            logger.info("empId $empId is deleted successfully @ ${dateInfo()}")
            "Employee Id: ${empEntity.empId} Employee Name: ${empEntity.empName} Delete Successful"
        }
        else {
            logger.warn("empId $empId is not found to delete @ ${dateInfo()}")
            "Deletion of emp failed"
        }
    }
    fun saveDepartment(@RequestBody department: Department): ResponseEntity<Any> {
        var deptEntity = DeptEntity()
        BeanUtils.copyProperties(department, deptEntity)
        return try{
            deptEntity=deptRepo.save(deptEntity)
            logger.info("Department name ${department.deptName} saved @ ${dateInfo()}")
            ResponseEntity<Any>(deptEntity, HttpStatus.CREATED)
        }
        catch(e:Exception){
            logger.error("Unable to Department @ ${dateInfo()}")
            ResponseEntity<Any>(StringBuilder("Unable to save Department"), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    fun fetchDepartment(@PathVariable deptId:Int):ResponseEntity<Any>?{
        return try{
            var deptEntity:DeptEntity=deptRepo.findOneBydeptId(deptId)!!
            logger.info("deptId ${deptEntity.deptId} is accessed @ ${dateInfo()}")
            ResponseEntity<Any>(deptEntity, HttpStatus.FOUND)
        }
        catch (e:Exception){
            logger.warn("Wrong deptId $deptId entered to access @ ${dateInfo()}")
            ResponseEntity<Any>(StringBuilder("No Emp Found for given deptId"),HttpStatus.NOT_FOUND)
        }
    }
    fun updateDepartment(@PathVariable deptId:Int, @RequestBody department: Department): String{
        var deptEntity:DeptEntity?
        return if(fetchDepartment(deptId)?.body is DeptEntity){
            deptEntity=fetchDepartment(deptId)?.body as DeptEntity
            BeanUtils.copyProperties(department, deptEntity)
            deptRepo.save(deptEntity)
            logger.info("Department name ${department.deptName} is updated @ ${dateInfo()}")
            "Department Id: ${deptEntity.deptId} Department Name: ${deptEntity.deptName}  Update Successful"
        }
        else{
            logger.info("Department name ${department.deptName} is not found to update @ ${dateInfo()}")
            "Department does not exist"
        }
    }
    fun deleteDepartment(@PathVariable deptId:Int):String{
        var deptEntity:DeptEntity?
            return if (fetchDepartment(deptId)?.body is DeptEntity){
                deptEntity=fetchDepartment(deptId)?.body as DeptEntity
                deptRepo.delete(deptEntity)
                logger.info("deptId $deptId is deleted successfully @ ${dateInfo()}")
                "Department Id: ${deptEntity.deptId} Department Name: ${deptEntity.deptName} Delete Successful"
            }
        else{
            logger.warn("deptId $deptId is not found to delete @ ${dateInfo()}")
            "Deletion of dept failed"
        }
    }
}