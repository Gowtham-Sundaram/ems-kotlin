package com.ems.ems_koltin.service_test;

import com.ems.ems_koltin.bean_dto.Department;
import com.ems.ems_koltin.bean_dto.Employee;
import com.ems.ems_koltin.entity_dao.dept_entity.DeptEntity;
import com.ems.ems_koltin.entity_dao.emp_entity.EmpEntity;
import com.ems.ems_koltin.repository.dept_repo.DepartmentRepo;
import com.ems.ems_koltin.repository.emp_repo.EmployeeRepo;
import com.ems.ems_koltin.service.EmpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTest {

    @Mock
    private EmployeeRepo empRepo;

    @Mock
    private DepartmentRepo deptRepo;

    @InjectMocks
    private EmpService empService;

    @Test
    public void saveEmployeeTest(){
        EmpEntity employeeEntity=new EmpEntity(1,"Gowtham",Long.parseLong("9944751745"),"gowtham@gmail.com", 1  );

        when(empRepo.save(ArgumentMatchers.any(EmpEntity.class))).thenReturn(employeeEntity);

        ResponseEntity<Object> empCreated=empService.saveEmployee(new Employee("Gowtham",Long.parseLong("9944751745"),"gowtham@gmail.com", 1  ));

        EmpEntity empCreated1=empRepo.save(employeeEntity);

        assertThat(((EmpEntity)(empCreated.getBody())).getEmpName()).isSameAs("Gowtham");

        verify(empRepo).save(employeeEntity);
    }

    @Test
    public void saveDepartmentTest(){
        DeptEntity deptEntity=new DeptEntity(1, "HPDC");
        when(deptRepo.save(ArgumentMatchers.any(DeptEntity.class))).thenReturn(deptEntity);
        ResponseEntity<Object> deptCreated=empService.saveDepartment(new Department("HPDC"));
        DeptEntity deptCreated1=deptRepo.save(deptEntity);

        assertThat(((DeptEntity)(deptCreated.getBody())).getDeptName()).isSameAs("HPDC");

        verify(deptRepo).save(deptEntity);
    }

    @Test
    public void fetchEmployeeTest(){
        EmpEntity employeeEntity=new EmpEntity(1,"Gowtham",Long.parseLong("9944751745"),"gowtham@gmail.com", 1  );
        when(empRepo.findOneByempId(ArgumentMatchers.any(Integer.class))).thenReturn(employeeEntity);
        ResponseEntity<Object> empCreated=empService.fetchEmployee(1);
        verify(empRepo).findOneByempId(1);
    }

    @Test
    public void fetchDepartmentTest(){
        DeptEntity deptEntity=new DeptEntity(1, "HPDC");
        when(deptRepo.findOneBydeptId(ArgumentMatchers.any(Integer.class))).thenReturn(deptEntity);
        ResponseEntity<Object> deptCreated=empService.fetchDepartment(1);
        verify(deptRepo).findOneBydeptId(1);
    }

    @Test
    public void updateEmployeeTest(){

        Employee employee=new Employee("Ram",Long.parseLong("9944751745"),"gowtham@gmail.com", 1  );

        EmpEntity employeeEntity=new EmpEntity(1,"Ram",Long.parseLong("9944751745"),"gowtham@gmail.com", 1 );
        when(empRepo.findOneByempId(ArgumentMatchers.any(Integer.class))).thenReturn(employeeEntity);

        String res=empService.updateEmployee(1,employee);

        Assertions.assertEquals("Employee Id: 1 Employee Name: Ram  Update Successful", res);

    }

    @Test
    public void updateDepartmentTest(){

        Department department=new Department( "LPDC");

        DeptEntity deptEntity=new DeptEntity(1,"LPDC");

        when(deptRepo.findOneBydeptId(ArgumentMatchers.any(Integer.class))).thenReturn(deptEntity);

        String res=empService.updateDepartment(1, department);
        Assertions.assertEquals("Department Id: 1 Department Name: LPDC  Update Successful", res);

    }

    @Test
    public void deleteEmployeeTest(){
        EmpEntity employeeEntity=new EmpEntity(1,"Ram",Long.parseLong("9944751745"),"gowtham@gmail.com", 1 );

        when(empRepo.findOneByempId(ArgumentMatchers.any(Integer.class))).thenReturn(employeeEntity);

        String res=empService.deleteEmployee(1);

        Assertions.assertEquals("Employee Id: 1 Employee Name: Ram Delete Successful",res);

    }

    @Test
    public void deleteDepatmentTest(){
        DeptEntity deptEntity=new DeptEntity(1,"LPDC");

        when(deptRepo.findOneBydeptId(ArgumentMatchers.any(Integer.class))).thenReturn(deptEntity);

        String res=empService.deleteDepartment(1);

        Assertions.assertEquals("Department Id: 1 Department Name: LPDC Delete Successful",res);

    }

}
