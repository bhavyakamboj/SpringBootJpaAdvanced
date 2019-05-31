package com.bhavyakamboj.springboot2.springboot2jpacrudexample;

import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.Employee;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED) //by default JPA tests are transactional, use this to disable transaction management for a test/whole class
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE) //to test against the actual registered database instead of in-memory datasoure
public class EmployeeRespositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveEmployee(){
        Employee employee = new Employee("Bhavya1234","Kamboj1234","bhavyakamboj1234@gmail.com");
        employeeRepository.save(employee);
        Employee foundEmployee = employeeRepository.findByFirstName("Bhavya1234");
        assertNotNull(foundEmployee);
        assertEquals(foundEmployee.getFirstName().toLowerCase().trim(),employee.getFirstName().toLowerCase().trim());
        assertEquals(foundEmployee.getLastName().toLowerCase().trim(),employee.getLastName().toLowerCase().trim());
    }

    @Test
    public void testDeleteEmployee(){
        Employee employee = new Employee("Deleted","Employee","deletedemployee@gmail.com");
        employeeRepository.save(employee);
        employeeRepository.delete(employee);
        assertNull(employeeRepository.findByFirstName("Deleted"));
    }

    @Test
    public void testGetEmployee(){
        Employee employee = new Employee("Bhavya123","Kamboj123","bhavyakamboj@gmail.com");
        assertNotNull(employee);
        assertEquals(employee.getFirstName(),"Bhavya123");
    }

    @Test
    public void testUpdateEmployee(){
        Employee employee = new Employee("Updated","Employee","updatedemployee@gmail.com");
        Employee createdEmployee = employeeRepository.save(employee);
        createdEmployee.setEmailID("updatedemployee2@gmail.com");
        createdEmployee.setFirstName("Updated2");
        createdEmployee.setLastName("Employee2");
        Employee updatedEmployee = employeeRepository.save(createdEmployee);
        assertEquals(updatedEmployee,createdEmployee);
    }
}
