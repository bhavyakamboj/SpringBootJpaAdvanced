package com.bhavyakamboj.springboot2.springboot2jpacrudexample.controller;

import com.bhavyakamboj.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.Employee;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import jdk.management.resource.ResourceRequestDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public List<Employee> getEmployeeByID(@PathVariable(value = "id",required = false) Long employeeId)throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee details not found for this id :: "+employeeId));
        logger.debug("returned a single employee");
        return Arrays.asList(employee);
    }
//@Valid annotation enables the hibernate validation
    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        logger.info("created an employee");
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id::" +employeeId));
        employee.setEmailID(employeeDetails.getEmailID());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        logger.info("updated an employee");
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String,Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found for id::" + employeeId));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        logger.info("deleted an employee");
        return response;
    }
}
